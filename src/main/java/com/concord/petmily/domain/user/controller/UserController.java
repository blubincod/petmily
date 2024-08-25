package com.concord.petmily.domain.user.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.common.dto.Pagination;
import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.service.UserService;
import com.concord.petmily.domain.walk.dto.WalkStatisticsDto;
import com.concord.petmily.domain.walk.dto.WalkWithPetsDto;
import com.concord.petmily.domain.walk.service.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 회원 관련 컨트롤러
 * [회원]
 * - 회원가입
 * - 회원 정보 조회
 * [산책 기록]
 * - 회원의 모든 반려동물의 산책 기록 조회
 * - 회원의 모든 반려동물별 전체 산책 통계 조회
 * - 회원의 모든 반려동물에 대한 종합적인 산책 통계 조회
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final WalkService walkService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        Long userId = userService.registerUser(addUserRequest);

        return new ResponseEntity<>(Map.of("userId", userId), HttpStatus.CREATED);
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * 회원의 모든 반려동물의 산책 특정 기간 기록 조회
     *
     * @param username  조회할 사용자의 아이디
     * @param startDate 조회 시작 날짜 (선택적, null 가능)
     * @param endDate   조회 종료 날짜 (선택적, null 가능)
     * @param pageable  페이지네이션 정보 (페이지 번호, 페이지 크기, 정렬 정보)
     *                  - page = 0: 기본 페이지 번호를 0으로 설정 (첫 페이지)
     *                  - size = 10: 한 페이지당 기본 항목 수를 10으로 설정
     *                  - sort = "startTime": 정렬 기준 필드를 산책 시작 시간인 "startTime"으로 설정
     *                  - direction = Sort.Direction.DESC: 정렬 방향을 내림차순으로 설정
     */
    @GetMapping("/{username}/walks")
    public ResponseEntity<ApiResponse<List<WalkWithPetsDto>>> getUserPetsWalks(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable // id는 walkId
    ) {
        Page<WalkWithPetsDto> walksPage = walkService.getUserPetsWalks(username, startDate, endDate, pageable);

        return ResponseEntity.ok(ApiResponse.success(walksPage));
    }
    /**
     * 회원의 모든 반려동물별 전체 산책 통계 조회
     *
     * @param username 조회할 사용자의 아이디 (URL 경로 변수)
     * @param pageable 페이지네이션 정보 (페이지 번호, 페이지 크기, 정렬 정보 포함)
     * @return 페이지네이션된 WalkStatisticsDto 객체를 포함한 ApiResponse
     */
    @GetMapping("/{username}/pets/walks/statistics")
    public ResponseEntity<ApiResponse<Page<WalkStatisticsDto>>> getUserPetsWalkStatistics(
            @PathVariable String username,
            @PageableDefault(page = 0, size = 10, sort = "petId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<WalkStatisticsDto> statisticsPage = walkService.getUserPetsWalkStatistics(username, pageable);

        ApiResponse<Page<WalkStatisticsDto>> response = new ApiResponse<>();
        response.setStatus("success");
        response.setData(statisticsPage);

        return ResponseEntity.ok(response);
    }

    /**
     * TODO getUserPetsOverallWalkStatistics
     * 회원의 모든 반려동물에 대한 종합적인 산책 통계 조회
     */
}