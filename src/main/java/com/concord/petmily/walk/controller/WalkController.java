package com.concord.petmily.walk.controller;

import com.concord.petmily.walk.dto.WalkLocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 산책 관련 컨트롤러
 * - 산책 위치 기록
 * - 산책 전체 정보 조회
 * - 산책 상세 정보 조회
 * - 산책 목표 설정 - 관리자
 * - 산책 목표 조회
 * - 산책 목표 선택
 */
@RestController
@RequestMapping("/api/v1/walks")
public class WalkController {
    /**
     * 산책 위치(위도, 경도, 측정된 시간) 저장
     *
     * @param
     * @return 생성된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @PostMapping("/location")
    public ResponseEntity<Map<String, Object>> saveLocation(@RequestBody WalkLocationDto location) {
        System.out.println("Received location: " + location.getLatitude() + location.getLongitude());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Location received successfully");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    /**
     * 산책 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getAllWalks() {
        // TODO 산책 목록 조회 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 정보 조회
     *
     * @return
     */
    @GetMapping("/{walkId}")
    public ResponseEntity<?> getWalkDetail() {
        // TODO 산책 정보 조회 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 목표 생성 - 관리자
     *
     * @return
     */
    @PostMapping("goals")
    public ResponseEntity<?> createWalkGoal() {
        // TODO 관리자 권한 확인 로직 추가
        // TODO 산책 목표 생성 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 목표 목록 조회
     * @return
     */
    @GetMapping("/goals")
    public ResponseEntity<?> getWalkGoals() {
        // TODO 사용 산책 목표 목록을 조회하는 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 목표 선택
     * @param userId
     * @param goalId
     * @return
     */
    @PostMapping("/goals/{goadId}")
    public ResponseEntity<Void> selectWalkGoal(@RequestParam Long userId, @RequestParam Long goalId) {
        // TODO 사용자가 산책 목표를 선택하는 로직
        return ResponseEntity.ok(null);
    }
}
