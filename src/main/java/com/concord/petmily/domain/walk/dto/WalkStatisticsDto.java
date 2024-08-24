package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.pet.entity.Pet;
import lombok.Builder;

@Builder
public class WalkStatisticsDto {
    private Long petId;
    private int totalWalks; // 총 산책 횟수
    private double totalDistance;
    private long totalDuration; // 분 단위
    private double averageDistance;
    private double averageDuration; // 분 단위
}
