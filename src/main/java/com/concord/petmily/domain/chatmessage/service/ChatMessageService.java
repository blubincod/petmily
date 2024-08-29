package com.concord.petmily.domain.chatmessage.service;

import com.concord.petmily.domain.chatmessage.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage saveMessage(String content, String username, Long openChatId);

    List<ChatMessage> getMessagesByOpenChatId(Long openChatId);

    List<ChatMessage> getMessagesByUserAndOpenChat(Long userId, Long openChatId);
}
