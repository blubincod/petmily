package com.concord.petmily.domain.chatmessage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String content;
    private Long openChatId;
}