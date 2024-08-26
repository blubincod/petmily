package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.walk.entity.WalkGoal;
import lombok.*;

import java.time.LocalTime;

/**
 * 산책 목표 관련 Dto
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalkGoalDto {
    private Long petId;  // WalkGoal의 ID(Pet 식별 관계)
    private String title;
    private int dailyTargetMinutes;
    private LocalTime targetStartTime;

    public static WalkGoalDto fromEntity(WalkGoal walkGoal) {
        return WalkGoalDto.builder()
                .petId(walkGoal.getPetId())
                .title(walkGoal.getTitle())
                .dailyTargetMinutes(walkGoal.getDailyTargetMinutes())
                .targetStartTime(walkGoal.getTargetStartTime())
                .build();
    }
}