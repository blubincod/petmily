package com.concord.petmily.domain.user.controller;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.service.UserService;
import com.concord.petmily.domain.walk.service.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 회원 관련 컨트롤러
 * [회원]
 * - 회원가입
 * - 회원 정보 조회
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
     * 사용자 삭제
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.deleteUser(username, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * 사용자 정지
     */
    @PutMapping("/suspend/{userId}")
    public ResponseEntity<String> suspendUser(@PathVariable Long userId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.suspendUser(username, userId);

        return ResponseEntity.status(HttpStatus.OK).body("회원 정지 완료");
    }

    /**
     * 사용자 정지 해제
     */
    @PutMapping("/unsuspend/{userId}")
    public ResponseEntity<String> unsuspendUser(@PathVariable Long userId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.unsuspendUser(username, userId);

        return ResponseEntity.status(HttpStatus.OK).body("회원 정지 해제 완료");
    }

    /**
     * 회원 통계 조회 (관리자 전용)
     */
    @GetMapping("/statistics")
    public ResponseEntity<String> statistics(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String result = userService.getStatistics(username);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}