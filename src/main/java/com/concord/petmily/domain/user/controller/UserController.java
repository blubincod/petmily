package com.concord.petmily.domain.user.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.domain.user.dto.AddAdminRequest;
import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 회원 관련 컨트롤러
 * <p>
 * - 회원가입
 * - 회원 정보 조회
 * - 회원 정보 수정
 * - 회원 Role, Status 변경
 * - 회원 삭제
 * - 회원 통계 조회
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param addUserRequest 회원가입 입력 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String, Long>>> registerUser(
            @Valid @RequestBody AddUserRequest addUserRequest
    ) {
        Long userId = userService.registerUser(addUserRequest);

        return ResponseEntity.ok(ApiResponse.success(Map.of("userId", userId)));
    }

    /**
     * 회원 정보 조회
     *
     * @param username 회원 번호
     */
    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<User>> getUserById(
            @PathVariable String username
    ) {
        User user = userService.findByUsername(username);

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 회원 정보 수정
     *
     * @param userId         회원 번호
     * @param addUserRequest 회원수정 입력 정보
     * @param userDetails    현재 인증된 사용자의 세부 정보
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long userId,
                                                        @Valid @RequestBody AddUserRequest addUserRequest,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.updateUser(username, userId, addUserRequest);

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 회원 Role, Status 변경 (관리자 전용)
     *
     * @param userId          회원 번호
     * @param addAdminRequest 회원수정 입력 정보
     * @param userDetails     현재 인증된 사용자의 세부 정보
     */
    @PutMapping("/admin/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUserByAdmin(@PathVariable Long userId,
                                                               @Valid @RequestBody AddAdminRequest addAdminRequest,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.updateUserByAdmin(username, userId, addAdminRequest);

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 회원 삭제
     *
     * @param userId      회원 번호
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.deleteUser(username, userId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 회원 통계 조회 (관리자 전용)
     *
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<String>> statistics(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String result = userService.getStatistics(username);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}