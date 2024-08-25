package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.ActivityType;
import com.concord.petmily.domain.walk.entity.WalkActivity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 산책 활동 로그 데이터 전송 객체
 */
@Data
@Getter
@Setter
@Builder
public class WalkActivityDto {

    private Long id;
    private Long petId; // 반려동물 아이디
    private double latitude; // 위도
    private double longitude; // 경도
    private ActivityType activity; // 활동 유형(대변, 소변, 수분 섭취)
    private LocalDateTime timestamp; // 타임스탬프

    public static WalkActivityDto fromEntity(WalkActivity walkActivity) {
        return WalkActivityDto.builder()
                .id(walkActivity.getId())
                .petId(walkActivity.getWalkingPet().getPet().getId())
                .latitude(walkActivity.getLatitude())
                .longitude(walkActivity.getLongitude())
                .activity(walkActivity.getActivityType())
                .timestamp(walkActivity.getTimestamp())
                .build();
    }
}
