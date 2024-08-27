package com.concord.petmily.domain.openchat.dto;

import java.time.LocalDateTime;

public class OpenChatDto {
    private Long id;
    private String name;
    private String description;
    private int currentParticipants;
    private int maxParticipants;
    private String creatorId;
    private LocalDateTime createdAt;
}
