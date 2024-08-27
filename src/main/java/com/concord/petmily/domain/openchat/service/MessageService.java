package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.domain.openchat.dto.MessageDto;
import com.concord.petmily.domain.openchat.dto.SendMessageDto;

import java.util.List;

public interface MessageService {
    /**
     * 메시지 전송
     */
    MessageDto sendMessage(Long chatId, SendMessageDto messageDto);

    /**
     * 메시지 목록 조회
     */
    List<MessageDto> getMessages(Long chatId);

}
