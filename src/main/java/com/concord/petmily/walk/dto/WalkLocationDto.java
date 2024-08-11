package com.concord.petmily.walk.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 산책 위치 데이터 전송 객체
 */
@Getter
public class WalkLocationDto {

    private double latitude; // 위도
    private double longitude; // 경도
    private LocalDateTime timestamp; // 타임스탬프

}
