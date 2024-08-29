package com.concord.petmily.domain.user.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.user.dto.AddAdminRequest;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.Role;
import com.concord.petmily.domain.user.entity.Status;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // username으로 회원 정보 조회
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    // userId로 회원 정보 조회
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    // 회원가입
    @Override
    public Long registerUser(AddUserRequest dto) {
        validateUniqueUser(dto.getUsername(), dto.getEmail());

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

    // 회원 정보 수정
    @Override
    public User updateUser(String username, Long targetId, AddUserRequest dto) {
        User user = findByUsername(username);

        User target = findById(targetId);

        // 사용자와 targetId가 다른 경우 에러
        if (!Objects.equals(user.getId(), targetId)) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        validateUniqueUser(dto.getUsername(), dto.getEmail());

        target.setUsername(dto.getUsername());
        target.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        target.setEmail(dto.getEmail());
        target.setAddress(dto.getAddress());
        target.setAge(dto.getAge());
        target.setBirthDate(dto.getBirthDate());
        target.setGender(dto.getGender());
        target.setName(dto.getName());
        target.setNickname(dto.getNickname());
        target.setPhone(dto.getPhone());

        return userRepository.save(target);
    }

    // 회원 Role, Status 변경 (관리자 전용)
    @Override
    public User updateUserByAdmin(String username, Long targetId, AddAdminRequest dto) {
        validateAdmin(findByUsername(username));

        User target = findById(targetId);

        // Role 설정
        try {
            target.setRole(Role.valueOf(dto.getRole()));
        } catch (IllegalArgumentException e) {
            throw new UserException(ErrorCode.INVALID_ROLE_TYPE);
        }

        // Status 설정
        try {
            target.setUserStatus(Status.valueOf(dto.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new UserException(ErrorCode.INVALID_STATUS_TYPE);
        }

        return userRepository.save(target);
    }

    // 회원 삭제 기능
    @Override
    public void deleteUser(String username, Long targetId) {
        User user = findByUsername(username);

        User target = findById(targetId);

        if(user.getRole() != Role.ADMIN) {
            // 사용자가 관리자가 아니라면
            // 사용자와 targetId가 다른 경우 에러
            if (!Objects.equals(user.getId(), targetId)) {
                throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
            }
        }

        // 회원 삭제시 user_status 변경
        target.setUserStatus(Status.DELETED);
        userRepository.save(target);
    }

    // 회원 통계 조회 (관리자 전용)
    @Override
    public String getStatistics(String username) {
        validateAdmin(findByUsername(username));

        // 총 사용자, 활성 사용자, 비활성 사용자, 정지 사용자, 삭제 사용자
        Long countTotalUsers = userRepository.count();
        Long countActiveUsers = userRepository.countByUserStatus(Status.ACTIVE);
        Long countInactiveUsers = userRepository.countByUserStatus(Status.INACTIVE);
        Long countSuspendedUsers = userRepository.countByUserStatus(Status.SUSPENDED);
        Long countDeletedUsers = userRepository.countByUserStatus(Status.DELETED);

        // 최근 한 달 및 일주일 이내 가입자 수
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        LocalDateTime oneWeekAgo = now.minusWeeks(1);

        Long countRegisteredWithinOneMonth = userRepository.countByRegisteredAtAfter(oneMonthAgo);
        Long countRegisteredWithinOneWeek = userRepository.countByRegisteredAtAfter(oneWeekAgo);

        // FIXME 줄발꿈 노출 변경
        String result = String.format(
                "총 사용자 : %d명%n" +
                        "활성 사용자 : %d명%n" +
                        "비활성 사용자 : %d명%n" +
                        "정지된 사용자 : %d명%n" +
                        "삭제된 사용자 : %d명%n" +
                        "최근 한달 이내 가입자 : %d명%n" +
                        "최근 일주일 이내 가입자 : %d명",
                countTotalUsers,
                countActiveUsers,
                countInactiveUsers,
                countSuspendedUsers,
                countDeletedUsers,
                countRegisteredWithinOneMonth,
                countRegisteredWithinOneWeek
        );

        return result;
    }

    // 관리자 계정인지 유효성 검사
    private void validateAdmin(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

    // UniqueUser인지 유효성 검사
    private void validateUniqueUser(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UserException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
