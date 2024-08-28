package com.concord.petmily.domain.notification.service;

import com.concord.petmily.domain.notification.dto.NotificationDto;
import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(String username, String lastEventId);
    void send(User user, Notification.NotificationType notificationType, String content);
    void sendBirthDateNotification();
    void sendAnnouncementToAllUsers(String username, String content);
    void sendMessageToUser(String username, NotificationDto.sendToUserDto dto);
    void sendWalkNotification();
}
