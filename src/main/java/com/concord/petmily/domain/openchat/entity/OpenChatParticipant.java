package com.concord.petmily.domain.openchat.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 오픈채팅 참가자 엔티티
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private ParticipantStatus status; // 참여자 상태
    
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;


    public OpenChatParticipant(User user, OpenChat openChat,ParticipantStatus status, LocalDateTime now) {
        this.user = user;
        this.openChat = openChat;
        this.joinedAt = now;
        this.status = status;
    }
}