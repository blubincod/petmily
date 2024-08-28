package com.concord.petmily.domain.openchat.dto;

import com.concord.petmily.domain.openchat.entity.OpenChat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenChatDto {
    private Long creatorId;
    private Long categoryId;
    private String title;
    private String description;
    private int maxParticipants;
    private int currentParticipants;
    private boolean isPublic;

    public static OpenChatDto fromEntity(OpenChat openChat) {
        return OpenChatDto.builder()
                .creatorId(openChat.getCreator().getId())
                .categoryId(openChat.getCategory().getId())
                .title(openChat.getTitle())
                .description(openChat.getDescription())
                .maxParticipants(openChat.getMaxParticipants())
                .currentParticipants(openChat.getCurrentParticipants())
                .isPublic(openChat.isPublic())
                .build();
    }
}
