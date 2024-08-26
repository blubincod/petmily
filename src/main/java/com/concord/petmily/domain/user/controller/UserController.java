package com.concord.petmily.domain.user.controller;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.service.UserService;
import com.concord.petmily.domain.walk.service.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}