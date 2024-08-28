package com.concord.petmily.domain.notification.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.notification.dto.NotificationDto;
import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.notification.repository.EmitterRepository;
import com.concord.petmily.domain.notification.repository.NotificationRepository;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.user.entity.Role;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    // SSE 연결 지속 시간 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Override
    public SseEmitter subscribe(String username, String lastEventId) {
        String emitterId = makeTimeIncludeId(username);

        // SseEmitter 객체 생성 및 저장
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // SseEmitter가 완료되거나 타임아웃될 때 해당 SseEmitter를 emitterRepository에서 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 클라이언트에게 더미 이벤트를 전송하여 연결이 생성되었음을 알림
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(username);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [username=" + username + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, username, emitterId, emitter);
        }

        return emitter;
    }

    // SseEmitter를 식별하기 위한 고유 아이디 생성
    // 데이터가 유실된 시점 파악을 위해 시각 데이터 들어감
    private String makeTimeIncludeId(String username) {
        return username + "_" + System.currentTimeMillis();
    }

    // 알림을 SseEmitter 객체로 전송
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            // 연결이 끊어졌다면 SseEmitter 객체를 제거하여 정리
            emitterRepository.deleteById(emitterId);
        }
    }

    // lastEventId가 비어있지 않으면 손실된 이벤트가 있음(true 리턴)
    // lastEventId가 비어있으면 손실된 이벤트가 없음(false 리턴)
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    // 수신자에게 전송되지 못한 이벤트 데이터를 캐시에서 가져와 클라이언트에게 전송
    private void sendLostData(String lastEventId, String userEmail, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUsername(String.valueOf(userEmail));
        eventCaches.entrySet().stream()
                // lastEventId가 엔트리 키보다 작을 때만 필터링(클라이언트가 수신하지 못한 이벤트 데이터를 필터링)
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    // 알림을 생성하고 지정된 수신자에게 알림을 전송하는 기능 수행
    @Override
    public void send(User user, Notification.NotificationType notificationType, String content) {
        Notification notification = notificationRepository.save(createNotification(user, notificationType, content));

        String username = user.getName();
        // eventId 생성 (SseEmitter로 전송되는 이벤트의 고유 식별자로 사용됨)
        String eventId = username + "_" + System.currentTimeMillis();
        // 수신자에 연결된 모든 SseEmitter 객체를 emitters 변수에 가져옴
        // 수신자가 여러 클라이언트와 연결된 경우를 대비해 다중 연결을 지원하기 위함
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUsername(username);
        // emitters를 순환하며 각 SseEmitter 객체에 알림 전송
        emitters.forEach(
                (key, emitter) -> {
                    // 해당 SseEmitter 객체에 이벤트 캐시를 저장
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDto.Response.createResponse(notification));
                }
        );
    }

    // 알림 객체 생성
    private Notification createNotification(User user, Notification.NotificationType notificationType, String content) {
        return Notification.builder()
                .user(user)
                .notificationType(notificationType)
                .content(content)
                .isRead(false)
                .build();
    }

    // 생일인 사용자에게 알림 전송
    // 생일인 반려동물의 사용자에게 알림 전송
    @Override
    public void sendBirthDateNotification() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<User> usersWithBirthDateToday = userRepository.findAllByBirthDate(today.format(formatter));
        for (User user : usersWithBirthDateToday) {
            send(user, Notification.NotificationType.BIRTHDATE, "생일 축하합니다!");
        }

        List<Pet> petWithBirthDateToday = petRepository.findAllByBirthDate(today);
        for (Pet pet : petWithBirthDateToday) {
            send(pet.getUser(), Notification.NotificationType.BIRTHDATE, pet.getName() + "의 생일을 축하합니다!");
        }
    }

    // 관리자가 모든 사용자에게 공지사항을 보냄
    @Override
    public void sendAnnouncementToAllUsers(String username, String content) {
        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 관리자가 아니라면 에러
        if(sender.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        List<User> users = userRepository.findAll(); // 모든 사용자 조회
        for (User user : users) {
            send(user, Notification.NotificationType.ANNOUNCEMENT, content);
        }
    }

    // 관리자가 특정 사용자에게 메시지를 보냄
    @Override
    public void sendMessageToUser(String username, NotificationDto.sendToUserDto dto) {
        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 관리자가 아니라면 에러
        if(sender.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        User user = userRepository.findByUsername(dto.getReceiver())
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        send(user, Notification.NotificationType.ANNOUNCEMENT, dto.getContent());
    }
}
