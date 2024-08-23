package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WalkWithPetsDto {
    private List<Long> petIds;
    private Long walkId;
    private double distance;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private WalkStatus status;

    // WalkWithPetsDto 객체를 생성
    public static WalkWithPetsDto fromEntity(Walk walk, List<Long> petIds) {
        return WalkWithPetsDto.builder()
                .petIds(petIds)
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .status(walk.getWalkStatus())
                .build();
    }
}