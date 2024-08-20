package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk_activity")
public class WalkActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 활동 아이디

    private double latitude; // 위도
    private double longitude; // 경도

    private ActivityType type; // 활동 유형(대변, 소변, 수분 섭취)

    private LocalDateTime timestamp; // 타임스탬프

    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk; // 산책

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet; // 반려동물

    public void setWalk(Walk walk) {
        this.walk = walk;
    }
}
