package com.concord.petmily.domain.openchat.dto;

import java.time.LocalDateTime;
public class ChatMessage {
    private String id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;

    // 생성자, getter, setter 등
}

enum MessageType {
    CHAT,
    JOIN,
    LEAVE
}
