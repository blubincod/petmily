package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class WalkWithPetsDto extends WalkDto {
    private List<Long> petIds;

    public static WalkWithPetsDto fromEntity(Walk walk, List<Long> petIds) {
        return WalkWithPetsDto.builder()
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .status(walk.getWalkStatus())
                .petIds(petIds)
                .build();
    }
}
