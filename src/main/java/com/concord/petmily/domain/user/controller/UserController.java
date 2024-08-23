package com.concord.petmily.domain.user.controller;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.service.UserService;
import com.concord.petmily.domain.user.service.UserServiceImpl;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.dto.WalkStatisticsDto;
import com.concord.petmily.domain.walk.dto.WalkWithPetsDto;
import com.concord.petmily.domain.walk.service.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * 회원의 모든 반려동물의 산책 기록 조회
     *
     * 옵션: 날짜 범위, 반려동물 ID
     */
    @GetMapping("/{userId}/walks")
    public ResponseEntity<List<WalkWithPetsDto>> getUserPetsWalks(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<WalkWithPetsDto> walks = walkService.getUserPetsWalks(userId, startDate, endDate);
        System.out.println(walks);
        return ResponseEntity.status(HttpStatus.OK).body(walks);
    }
}