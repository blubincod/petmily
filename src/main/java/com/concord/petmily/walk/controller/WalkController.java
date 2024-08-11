package com.concord.petmily.walk.controller;

import com.concord.petmily.walk.dto.WalkLocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 산책 관련 컨트롤러
 * <p>
 * - 산책 위도, 경도 가져오기
 * - 산책 전체 정보 조회
 * - 산책 상세 정보 조회
 * - 산책 목표 설정 (관리자)
 * - 산책 목표 조회
 */

@RestController
@RequestMapping("/api/v1/walks")
public class WalkController {

    /**
     * 산책 위치(위도, 경도) 저장
     *
     * @param
     * @return 생성된 게시물의 정보와 HTTP 200 상태를 반
     */
    @PostMapping("/location")
    public ResponseEntity<Map<String, Object>> saveLocation(@RequestBody WalkLocationDto location) {
        System.out.println("Received location: " + location.getLatitude() + location.getLongitude());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Location received successfully");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
