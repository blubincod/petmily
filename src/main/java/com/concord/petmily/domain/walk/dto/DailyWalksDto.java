package com.concord.petmily.domain.walk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class DailyWalksDto {
    private LocalDate date; // 조회된 날짜
    private List<WalkDto> walks;

    // 해당 날짜의 총 산책 거리
    public double getTotalDistance() {
        return walks.stream().mapToDouble(WalkDto::getDistance).sum();
    }

    // 해당 날짜의 총 산책 시간
    public long getTotalDuration() {
        return walks.stream().mapToLong(WalkDto::getDuration).sum();
    }

    // 해당 날짜의 산책 횟수
    public int getWalkCount() {
        return walks.size();
    }
}
