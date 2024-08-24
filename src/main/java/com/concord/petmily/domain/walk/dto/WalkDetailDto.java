package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import com.concord.petmily.domain.walk.entity.WalkingPet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WalkDetailDto {
    private Long walkId;
    private double distance;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private WalkStatus status;
    private List<WalkingPetDto> pets;
    private List<WalkActivityDto> activities;

    public static WalkDetailDto fromEntity(Walk walk, List<WalkingPet> pets, List<WalkActivityDto> activities) {
        return WalkDetailDto.builder()
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .status(walk.getWalkStatus())
//                .pets(pets.stream().map(WalkingPetDto::fromEntity).collect(Collectors.toList()))
                .activities(activities)
                .build();
    }
}
