package com.concord.petmily.domain.openchat.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 오픈채팅 엔티티
 */
@Entity
public class OpenChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String password;
    private int maxParticipants;
    private int currentParticipants;
    private boolean isPublic;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ChatStatus status;

    private LocalDateTime lastMessageAt;

    private String imageUrl; // 오픈채팅 방 이미지 경로

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator; // 방장 (생성자)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private OpenChatCategory category;
}