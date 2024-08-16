package com.concord.petmily.user.service;

import com.concord.petmily.auth.exception.AuthException;
import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.user.dto.AddUserRequest;
import com.concord.petmily.user.entity.User;
import com.concord.petmily.user.exception.CustomExceptions;
import com.concord.petmily.user.exception.UserException;
import com.concord.petmily.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // FIXME role, status ENUM으로 처리
        String role = dto.getRole() != null ? dto.getRole() : "USER";  // 관리자가 아닌 경우 기본값 "USER"
        String status = dto.getStatus() != null ? dto.getStatus() : "ACTIVE";  // 상태는 기본값 "ACTIVE"

        User user = User.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .address(dto.getAddress())
                .age(dto.getAge())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .role(role)  // 기본값 설정
                .status(status)  // 기본값 설정
                .build();

        return userRepository.save(user).getUserId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
}
