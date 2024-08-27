package com.concord.petmily.domain.openchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomDto {
    private Long categoryId;
    private String title;
    private String description;
    private String password;
    private int maxParticipants;
    private boolean isPublic;
}
