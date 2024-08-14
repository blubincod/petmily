package com.concord.petmily.user.service;

import com.concord.petmily.user.dto.AddUserRequest;
import com.concord.petmily.user.entity.User;
import com.concord.petmily.user.exception.CustomExceptions;
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
            throw new CustomExceptions.DuplicateUsernameException("이미 존재하는 아이디 입니다!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomExceptions.DuplicateEmailException("이미 존재하는 이메일 입니다!");
        }

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
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다: " + userId));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다!: " + username));
    }
}
