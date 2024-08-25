package com.concord.petmily.domain.walk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalkStatisticsDto {
    private Long petId;
    private int totalWalks; // 총 산책 횟수
    private double totalDistanceKm; // 총 산책 거리(KM)
    private long totalDurationMinutes; // 총 산책 시간(Minutes)
    private double averageDistanceKm; // 평균 산책 거리(KM)
    private long averageDurationMinutes; // 평균 산책 시간(Minutes)
}
