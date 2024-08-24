package com.concord.petmily.domain.user.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.Role;
import com.concord.petmily.domain.user.entity.Status;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long registerUser(AddUserRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

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
                .role(Role.USER)  // 기본값 설정
                .userStatus(Status.ACTIVE)  // 기본값 설정
                .isWalking(false)
                .build();

        return userRepository.save(user).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    // TODO 중복 기능?
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
}
