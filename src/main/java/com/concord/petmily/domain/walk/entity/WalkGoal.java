package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk_goal")
public class WalkGoal {
    @Id
    private Long petId; // Pet의 ID를 직접 사용

    @OneToOne
    @MapsId // @Id로 지정된 petId 필드와 pet 엔티티의 ID를 동기화
    @JoinColumn(name = "pet_id") // 외래 키(FK) 컬럼을 지정
    private Pet pet; // 반려동물 테이블과 일대일 식별 관계

    private String title; // 산책 목표 제목
    private int dailyTargetMinutes; // 일일 목표 산책 시간(분)
    private LocalTime targetStartTime; // 목표 산책 시작 시간

}
