package com.concord.petmily.domain.notification.service;

import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(String username, String lastEventId);
    void send(User user, Notification.NotificationType notificationType, String content);
}
