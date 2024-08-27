package com.concord.petmily.domain.openchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomDto {
    private Long categoryId;
    private Long creatorId;
    private String title;
    private String description;
    private int maxParticipants;
}
