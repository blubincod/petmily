package com.concord.petmily.walk.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "walk")
public class Walk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 아이디

    private Long distance; // 산책 거리

    private LocalDateTime startTime; // 산책 시작 시간
    private LocalDateTime endTime; // 산책 종료 시간

}
