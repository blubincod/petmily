package com.concord.petmily.domain.walk.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "walk_activity")
public class WalkActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 활동 아이디

    private double latitude; // 위도
    private double longitude; // 경도

    @Enumerated(EnumType.STRING)
    private ActivityType activityType; // 활동 유형(대변, 소변, 수분 섭취)

    private LocalDateTime timestamp; // 타임스탬프

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "walk_id", referencedColumnName = "walk_id"),
            @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    })
    private WalkParticipant walkParticipant;
}