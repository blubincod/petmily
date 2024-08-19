package com.concord.petmily.walk.controller;

import com.concord.petmily.user.entity.User;
import com.concord.petmily.walk.dto.WalkActivityDto;
import com.concord.petmily.walk.dto.WalkDto;
import com.concord.petmily.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 산책 관련 컨트롤러
 * - 산책 위치 기록
 * - 산책 전체 정보 조회
 * - 산책 상세 정보 조회
 * - 산책 목표 설정 (관리자)
 * - 산책 목표 조회
 * - 산책 목표 선택
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/walks")
public class WalkController {

    private final WalkService walkService;

    @PostMapping
    public ResponseEntity<?> startWalk(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalkDto walkDto
    ) {
        String username = userDetails.getUsername();
        System.out.println(username);

        WalkDto createdWalk = walkService.startWalk(username, walkDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdWalk);
    }

    @PutMapping("/{walkId}/end")
    public ResponseEntity<?> endWalk(
            @PathVariable Long walkId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalkDto walkDto
    ) {
        System.out.println("**************" + walkId + "***************");
        WalkDto updatedWalk = walkService.endWalk(walkId, walkDto);

        return ResponseEntity.ok(updatedWalk);
    }

    /**
     * 산책 활동 저장
     *
     * @param
     * @return 생성된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @PostMapping("/{walkId}/activities")
    public ResponseEntity<Map<String, Object>> logWalkActivity(
            @PathVariable Long walkId,
            @RequestBody WalkActivityDto walkActivityDto
    ) {
        WalkActivityDto savedWalkActivity = walkService.logWalkActivity(walkId, walkActivityDto);

        Map<String, Object> response = new HashMap<>();

        response.put("activity", savedWalkActivity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 산책 기록 목록 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getMyWalks() {
        // TODO 산책 목록 조회 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 상세 정보 조회
     *
     * @return
     */
    @GetMapping("/{walkId}")
    public ResponseEntity<?> getMyWalk(
            @PathVariable String walkId) {
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
     *
     * @return
     */
    @GetMapping("/goals")
    public ResponseEntity<?> getWalkGoals() {
        // TODO 사용 산책 목표 목록을 조회하는 로직
        return ResponseEntity.ok(null);
    }

    /**
     * 산책 목표 선택
     *
     * @param userId
     * @param goalId
     * @return
     */
    @PostMapping("/goals/{goalId}")
    public ResponseEntity<Void> selectWalkGoal(
            @RequestParam Long userId, @PathVariable Long goalId) {
        // TODO 사용자가 산책 목표를 선택하는 로직
        return ResponseEntity.ok(null);
    }
}
