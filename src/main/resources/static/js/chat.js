let stompClient = null;

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage(message) {
    stompClient.send("/app/chat", {}, JSON.stringify({'message': message}));
}

function showMessage(message) {
    let messageContainer = document.getElementById('message-container');
    let messageElement = document.createElement('p');
    messageElement.textContent = message;
    messageContainer.appendChild(messageElement);
}

document.getElementById('message-form').addEventListener('submit', function(event) {
    event.preventDefault();
    let messageInput = document.getElementById('message');
    if(messageInput.value.trim() !== '') {
        sendMessage(messageInput.value);
        messageInput.value = '';
    }
});

// 페이지 로드 시 연결
connect();