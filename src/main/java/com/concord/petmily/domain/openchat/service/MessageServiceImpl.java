package com.concord.petmily.domain.openchat.service;

import com.concord.petmily.domain.openchat.dto.MessageDto;
import com.concord.petmily.domain.openchat.dto.SendMessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Override
    public MessageDto sendMessage(Long chatId, SendMessageDto messageDto) {
        return null;
    }

    @Override
    public List<MessageDto> getMessages(Long chatId) {
        return null;
    }
}
