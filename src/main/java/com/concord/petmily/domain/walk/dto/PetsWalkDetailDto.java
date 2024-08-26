package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PetsWalkDetailDto {
    private List<Long> petIds;
    private Long walkId;
    private double distance;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate walkDate;
    private WalkStatus status;

    // WalkWithPetsDto 객체를 생성
    public static PetsWalkDetailDto fromEntity(Walk walk, List<Long> petIds) {
        return PetsWalkDetailDto.builder()
                .petIds(petIds)
                .walkId(walk.getId())
                .distance(walk.getDistance())
                .duration(walk.getDuration())
                .startTime(walk.getStartTime())
                .endTime(walk.getEndTime())
                .walkDate(walk.getWalkDate())
                .status(walk.getWalkStatus())
                .build();
    }

}
