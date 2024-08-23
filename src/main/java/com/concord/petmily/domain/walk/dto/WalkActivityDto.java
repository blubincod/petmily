package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.walk.entity.ActivityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 산책 활동 로그 데이터 전송 객체
 */
@Getter
@Setter
public class WalkActivityDto {

    private Long petId; // 반려동물 아이디
    private double latitude; // 위도
    private double longitude; // 경도
    private ActivityType activity; // 활동 유형(대변, 소변, 수분 섭취)
    private LocalDateTime timestamp; // 타임스탬프

}
