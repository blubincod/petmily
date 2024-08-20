package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 산책 데이터 전송 객체
 */
@Data
@Builder
public class WalkDto {
    private Long walkId;
    private double distance; // 산책 거리
    private double duration; // 산책 총 시간
    private LocalDateTime startTime; // 산책 시작 시간
    private LocalDateTime endTime; // 산책 종료 시간
    private WalkStatus status; // 산책 진행 상태

    // Walk Entity를 WalkDto로 변환하는 메서드
    public static WalkDto fromEntity(Walk walk){
        return WalkDto.builder()
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .status(walk.getStatus())
                .build();
    }
}
