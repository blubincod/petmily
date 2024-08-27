package com.concord.petmily.domain.notification.dto;

import com.concord.petmily.domain.notification.entity.Notification;
import lombok.*;

public class NotificationDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        String id;
        String name;
        String content;
        String type;
        String createdAt;
        public static Response createResponse(Notification notification) {
            return Response.builder()
                    .content(notification.getContent())
                    .id(notification.getId().toString())
                    .name(notification.getUser().getName())
                    .createdAt(notification.getCreatedAt().toString())
                    .build();

        }
    }
}
