package com.concord.petmily.domain.chatmessage.service;

import com.concord.petmily.domain.chatmessage.entity.ChatMessage;
import com.concord.petmily.domain.chatmessage.repository.ChatMessageRepository;
import com.concord.petmily.domain.openchat.entity.OpenChatParticipant;
import com.concord.petmily.domain.openchat.repository.OpenChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final OpenChatParticipantRepository openChatParticipantRepository;

    @Override
    @Transactional
    public ChatMessage saveMessage(String content, Long userId, Long openChatId) {
        System.out.println(userId);
        System.out.println(openChatId);
        System.out.println(content);
        OpenChatParticipant sender = openChatParticipantRepository.findByUserIdAndOpenChatId(userId, openChatId)
                .orElseThrow(() -> new RuntimeException("현재 사용자가 채팅방에 참여하고 있지 않습니다."));

        ChatMessage message = new ChatMessage();
        message.setContent(content);
        message.setSender(sender);
        message.setOpenChat(sender.getOpenChat());
        message.setSentAt(LocalDateTime.now());

        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByOpenChatId(Long openChatId) {
        return chatMessageRepository.findByOpenChatIdOrderBySentAtAsc(openChatId);
    }

    @Override
    public List<ChatMessage> getMessagesByUserAndOpenChat(Long userId, Long openChatId) {
        return chatMessageRepository.findBySender_User_IdAndOpenChat_IdOrderBySentAtAsc(userId, openChatId);
    }
}
