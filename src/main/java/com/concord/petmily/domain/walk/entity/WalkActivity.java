package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
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
    private WalkingPet walkingPet;
}