// WebSocket 클라이언트 객체를 저장할 변수
let stompClient = null;

// URL에서 채팅방 ID 추출
function getChatRoomId() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}

// WebSocket 서버에 연결하는 함수
function connect() {
    // SockJS를 사용하여 WebSocket 연결 생성
    let socket = new SockJS('/ws');
    // Stomp 클라이언트 생성
    stompClient = Stomp.over(socket);

    // 연결 시 사용할 헤더 (인증 토큰 포함)
    let headers = {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
    };

    let token = localStorage.getItem('token');
    if (!token) {
        console.error('No token found');
        window.location.href = '/login.html';
        return;
    }

    // Stomp 클라이언트로 서버에 연결
    stompClient.connect(headers,
        // 연결 성공 시 콜백
        function (frame) {
            console.log('Connected: ' + frame);
            // 메시지 토픽 구독
            stompClient.subscribe('/topic/messages', function (message) {
                showMessage(JSON.parse(message.body).content);
            });
        },
        // 연결 실패 시 콜백
        function (error) {
            console.error('STOMP error:', error);
            // 인증 오류 시 로그인 페이지로 리다이렉트
            if (error.headers && error.headers.message === 'Unauthorized') {
                window.location.href = '/login.html';
            }
        }
    );
}

// WebSocket 연결 해제 함수
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

// 메시지 전송 함수
function sendMessage(message) {
    if (stompClient && stompClient.connected) {
        let token = localStorage.getItem('token');
        // 메시지 전송 시 인증 토큰 포함
        stompClient.send("/app/chat", {'Authorization': 'Bearer ' + token}, JSON.stringify({'message': message}));
    } else {
        console.error('STOMP client is not connected');
    }
}

// 수신된 메시지를 화면에 표시하는 함수
function showMessage(message) {
    let messageContainer = document.getElementById('message-container');
    let messageElement = document.createElement('p');
    messageElement.textContent = message;
    messageContainer.appendChild(messageElement);
}

// 메시지 입력 폼 제출 이벤트 리스너
document.getElementById('message-form').addEventListener('submit', function (event) {
    event.preventDefault();
    let messageInput = document.getElementById('message');
    if (messageInput.value.trim() !== '') {
        sendMessage(messageInput.value);
        messageInput.value = '';
    }
});

// 페이지 로드 시 실행되는 함수
window.onload = function () {
    let token = localStorage.getItem('token');
    // 토큰이 없으면 로그인 페이지로 리다이렉트
    if (!token) {
        window.location.href = '/login.html';
    } else {
        // 토큰이 있으면 WebSocket 연결 시도
        connect();
    }
};