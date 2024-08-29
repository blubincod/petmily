package com.concord.petmily.domain.chatmessage.repository;

import com.concord.petmily.domain.chatmessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByOpenChatIdOrderBySentAtAsc(Long openChatId);

    List<ChatMessage> findBySender_User_IdAndOpenChat_IdOrderBySentAtAsc(Long userId, Long openChatId);
}
