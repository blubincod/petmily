package com.concord.petmily.domain.openchat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 오픈채팅 카테고리 엔티티
 */
@Getter
@Setter
@Entity
public class OpenChatCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}
