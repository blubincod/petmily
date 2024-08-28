package com.concord.petmily.domain.openchat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 오픈채팅 메시지 엔티티
 */
@Entity
public class OpenChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime sentAt;

    @ManyToOne
    private OpenChat openChat;

    @ManyToOne
    private OpenChatParticipant sender;
}
