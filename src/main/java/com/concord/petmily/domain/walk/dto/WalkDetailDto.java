package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class WalkDetailDto {
    private Long walkId;
    private double distance;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private WalkStatus status;
    private List<Long> pets;
    private List<WalkActivityDto> activities;

    public static WalkDetailDto fromEntity(Walk walk, List<Long> petIds, List<WalkActivityDto> activities) {
        return WalkDetailDto.builder()
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .status(walk.getWalkStatus())
                .pets(petIds)
                .activities(activities)
                .build();
    }
}
