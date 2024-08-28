package com.concord.petmily.domain.openchat.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 오픈채팅 엔티티
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // 채팅 참가자 증가
    public void incrementParticipants() {
        if (this.currentParticipants < this.maxParticipants) { // 최대 참가자 수 보다 작아야 증가
            this.currentParticipants++;
        }
    }
    // 채팅 참가자 감소
    public void decrementParticipants() {
        if (this.currentParticipants > 0) {
            this.currentParticipants--;
        }
    }
}