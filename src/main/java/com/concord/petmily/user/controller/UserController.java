package com.concord.petmily.user.controller;

import com.concord.petmily.user.dto.AddUserRequest;
import com.concord.petmily.user.entity.User;
import com.concord.petmily.user.exception.CustomExceptions;
import com.concord.petmily.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        try {
            Long userId = userService.save(addUserRequest);
            return new ResponseEntity<>(Map.of("userId", userId), HttpStatus.CREATED);
        } catch (CustomExceptions.DuplicateUsernameException | CustomExceptions.DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 실패: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            Long id = Long.parseLong(userId);
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("잘못된 유저 아이디 형식입니다");
        }
    }
}