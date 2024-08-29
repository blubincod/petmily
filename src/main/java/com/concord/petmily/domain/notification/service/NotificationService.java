package com.concord.petmily.domain.notification.service;

import com.concord.petmily.domain.notification.dto.NotificationDto;
import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    // SSE 구독 연결
    SseEmitter subscribe(String username, String lastEventId);
    // 알림을 생성하고 전송
    void send(User user, Notification.NotificationType notificationType, String content);
    // 생일 알림
    void sendBirthDateNotification();
    // 모든 사용자에게 공지사항
    void sendAnnouncementToAllUsers(String username, String content);
    // 특정 사용자에게 메시지
    void sendMessageToUser(String username, NotificationDto.sendToUserDto dto);
    // 산책 알림
    void sendWalkNotification();
}
