package com.concord.petmily.domain.chatmessage.entity;

import com.concord.petmily.domain.openchat.entity.OpenChat;
import com.concord.petmily.domain.openchat.entity.OpenChatParticipant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sender_user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "sender_open_chat_id", referencedColumnName = "open_chat_id")
    })
    private OpenChatParticipant sender;

    @ManyToOne
    @JoinColumn(name = "open_chat_id")
    private OpenChat openChat;
}