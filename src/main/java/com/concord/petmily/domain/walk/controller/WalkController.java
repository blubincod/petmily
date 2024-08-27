package com.concord.petmily.domain.walk.controller;

import com.concord.petmily.common.dto.PagedApiResponse;
import com.concord.petmily.domain.walk.dto.*;
import com.concord.petmily.domain.walk.service.WalkGoalService;
import com.concord.petmily.domain.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 산책 관련 컨트롤러
 * <p>
 * [산책 기록]
 * - 산책 시작 및 산책 정보 기록
 * - 산책 종료 및 산책 정보 기록
 * - 산책 활동 기록
 * <p>
 * [산책 기록 조회]
 * - 산책 상세 기록 조회
 * - 반려동물 기간별 일일 산책 목록과 요약 조회
 * - 반려동물의 기간별 일일 산책 통계 조회
 * - 회원의 모든 반려동물의 특정 기간 산책 기록 조회
 * - 회원의 모든 반려동물별 전체 산책 통계 조회
 * - 회원의 모든 반려동물에 대한 종합적인 산책 통계 조회
 * <p>
 * [산책 목표]
 * - 산책 목표 설정
 * - 산책 목표 조회
 * - 산책 목표 수정
 * - 산책 목표 삭제
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/walks")
public class WalkController {

    private final WalkService walkService;
    private final WalkGoalService walkGoalService;

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
     * 특정 산책 상세 조회
     */
    @GetMapping("/{walkId}")
    public ResponseEntity<?> getWalkDetail(@PathVariable Long walkId) {
        WalkDetailDto walkDetail = walkService.getWalkDetail(walkId);

        return ResponseEntity.ok(walkDetail);
    }

    /**
     * 반려동물 기간별 일일 산책 목록 조회
     * TODO 응답을 더 체계적으로 정리
     */
    @GetMapping("/pets/{petId}/walks")
    public ResponseEntity<PagedApiResponse<List<DailyWalksDto>>> getPetDailyWalks(
            @PathVariable Long petId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<DailyWalksDto> walks = walkService.getPetDailyWalks(petId, userDetails.getUsername(), startDate, endDate, pageable);
        return ResponseEntity.ok(PagedApiResponse.success(walks));
    }

    /**
     * 반려동물의 기간별 일일 산책 통계 조회
     */
    @GetMapping("/pets/{petId}/statistics/daily")
    public ResponseEntity<PagedApiResponse<List<WalkStatisticsDto>>> getPetDailyWalksStatistics(
            @PathVariable Long petId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<WalkStatisticsDto> statistics = walkService.getPetDailyWalksStatistics(
                petId, userDetails.getUsername(), startDate, endDate, pageable
        );
        return ResponseEntity.ok(PagedApiResponse.success(statistics));
    }

    /**
     * 회원의 모든 반려동물의 특정 기간 산책 기록 상세 조회
     *
     * @param startDate 조회 시작 날짜 (선택적, null 가능)
     * @param endDate   조회 종료 날짜 (선택적, null 가능)
     * @param pageable  페이지네이션 정보 (페이지 번호, 페이지 크기, 정렬 정보)
     *                  - page = 0: 기본 페이지 번호를 0으로 설정 (첫 페이지)
     *                  - size = 10: 한 페이지당 기본 항목 수를 10으로 설정
     *                  - sort = "startTime": 정렬 기준 필드를 산책 시작 시간인 "startTime"으로 설정
     *                  - direction = Sort.Direction.DESC: 정렬 방향을 내림차순으로 설정
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<PagedApiResponse<List<PetsWalkDetailDto>>> getUserAllPetsWalksDetail(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable // id: walkId
    ) {
        Page<PetsWalkDetailDto> walksPage = walkService.getUserAllPetsWalksDetail(username, startDate, endDate, pageable);

        return ResponseEntity.ok(PagedApiResponse.success(walksPage));
    }

    /**
     * 회원의 모든 반려동물별 전체 산책 통계 조회
     *
     * @param pageable 페이지네이션 정보 (페이지 번호, 페이지 크기, 정렬 정보)
     */
    @GetMapping("/users/{username}/statistics")
    public ResponseEntity<PagedApiResponse<List<WalkStatisticsDto>>> getUserPetsWalkStatistics(
            @PathVariable String username,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable // id: petId
    ) {
        Page<WalkStatisticsDto> petsStatisticsPage = walkService.getUserPetsWalkStatistics(username, pageable);

        return ResponseEntity.ok(PagedApiResponse.success(petsStatisticsPage));
    }

    /**
     * TODO 회원의 모든 반려동물에 대한 종합적인 산책 통계 조회
     */
    @GetMapping("/users/{username}/statistics/overall")
    public ResponseEntity<PagedApiResponse<?>> getUserPetsOverallWalkStatistics(
            @PathVariable String username
    ) {
//        OverallWalkStatisticsDto overallStatistics = walkService.getUserPetsOverallWalkStatistics(username);
        return ResponseEntity.ok(PagedApiResponse.success(null));
    }

    /**
     * 산책 목표 생성
     */
    @PostMapping("/pets/{petId}/goal")
    public ResponseEntity<WalkGoalDto> createWalkGoal(
            @PathVariable Long petId,
            @RequestBody WalkGoalDto walkGoalDto) {
        WalkGoalDto createdGoal = walkGoalService.createWalkGoal(petId, walkGoalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal);
    }

    /**
     * 산책 목표 조회
     */
    @GetMapping("/pets/{petId}/goal")
    public ResponseEntity<WalkGoalDto> getWalkGoal(@PathVariable Long petId) {
        WalkGoalDto goal = walkGoalService.getWalkGoal(petId);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }

    /**
     * 산책 목표 수정
     */
    @PutMapping("/pets/{petId}/goal")
    public ResponseEntity<WalkGoalDto> updateWalkGoal(
            @PathVariable Long petId,
            @RequestBody WalkGoalDto walkGoalDto) {
        WalkGoalDto updatedGoal = walkGoalService.updateWalkGoal(petId, walkGoalDto);
        return ResponseEntity.ok(updatedGoal);
    }

    /**
     * 산책 목표 삭제
     */
    @DeleteMapping("/pets/{petId}/goal")
    public ResponseEntity<Void> deleteWalkGoal(@PathVariable Long petId) {
        walkGoalService.deleteWalkGoal(petId);
        return ResponseEntity.noContent().build();
    }
}
