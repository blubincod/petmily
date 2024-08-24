package com.concord.petmily.domain.walk.controller;

import com.concord.petmily.domain.walk.dto.StartWalkRequest;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDetailDto;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 산책 관련 컨트롤러
 * <p>
 * - 산책 시작 및 산책 정보 기록
 * - 산책 종료 및 산책 정보 기록
 * - 산책 활동 기록
 * - 산책 전체 정보 조회
 * - 산책 상세 정보 조회
 * - 산책 목표 설정
 * - 산책 목표 조회
 * - 산책 목표 선택
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/walks")
public class WalkController {

    private final WalkService walkService;

    /**
     * 산책 시작 및 산책 정보 기록
     *
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @PostMapping
    public ResponseEntity<WalkDto> startWalk(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody StartWalkRequest startWalkRequest
    ) {
        String username = userDetails.getUsername();
        List<Long> petIds = startWalkRequest.getPetIds();
        LocalDateTime startTime = startWalkRequest.getStartTime();

        WalkDto createdWalk = walkService.startWalk(username, petIds, startTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdWalk);
    }

    /**
     * 산책 종료 및 산책 정보 기록
     */
    @PutMapping("/{walkId}/end")
    public ResponseEntity<WalkDto> endWalk(
            @PathVariable Long walkId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalkDto walkDto
    ) {
        String username = userDetails.getUsername();
        WalkDto updatedWalk = walkService.endWalk(walkId, username, walkDto);

        return ResponseEntity.ok(updatedWalk);
    }

    /**
     * 산책 활동 기록
     */
    @PostMapping("/{walkId}/activities")
    public ResponseEntity<Map<String, Object>> logWalkActivity(
            @PathVariable Long walkId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WalkActivityDto walkActivityDto
    ) {
        String username = userDetails.getUsername();
        WalkActivityDto savedWalkActivity = walkService.logWalkActivity(walkId, username, walkActivityDto);

        Map<String, Object> response = new HashMap<>();

        response.put("activity", savedWalkActivity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 산책 기록 상세 조회
     */
    @GetMapping("/{walkId}")
    public ResponseEntity<?> getWalkDetail(@PathVariable Long walkId) {
        WalkDetailDto walkDetail = walkService.getWalkDetail(walkId);

        return ResponseEntity.ok(walkDetail);
    }
//    /**
//     * 회원의 모든 반려동물의 산책 기록 조회
//     */
//    @GetMapping("/pets")
//    public ResponseEntity<List<WalkDto>> getUserPetsWalks(
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        List<WalkDto> userPetsWalks = walkService.getUserPetsWalks(userDetails.getUsername());
//        System.out.println(userPetsWalks);
//        return ResponseEntity.status(HttpStatus.OK).body(userPetsWalks);
//    }
//
//    /**
//     * 반려동물의 전체 산책 기록 조회
//     */
//    @GetMapping("/pets/{petId}")
//    public ResponseEntity<List<WalkDto>> getPetWalks(
//            @PathVariable Long petId) {
//        List<WalkDto> petWalks = walkService.getPetWalks(petId);
//        System.out.println(petWalks);
//        return ResponseEntity.status(HttpStatus.OK).body(petWalks);
//    }

//    /**
//     * TODO 반려동물의 특정 산책 정보를 조회
//     */
//    @GetMapping("/{walkId}/pets/{petId}")
//    public ResponseEntity<List<WalkDto>> getPetWalk(
//            @PathVariable Long petId) {
//        List<WalkDto> petWalks = walkService.getPetWalks(petId);
//
//        return ResponseEntity.status(HttpStatus.OK).body(petWalks);
//    }

    /**
     * 산책 목표 생성
     *
     * @return
     */
    @PostMapping("goals")
    public ResponseEntity<?> createWalkGoal() {
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
