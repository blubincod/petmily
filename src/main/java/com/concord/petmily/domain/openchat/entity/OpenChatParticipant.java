package com.concord.petmily.domain.openchat.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 오픈채팅 참가자 엔티티
 */
@Entity
@IdClass(OpenChatParticipantId.class)
public class OpenChatParticipant {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "open_chat_id")
    private OpenChat openChat;

    private LocalDateTime joinedAt;
    private boolean active = true;
}
