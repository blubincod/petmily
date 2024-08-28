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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void deleteUser(String username, Long targetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

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

    @Override
    public void suspendUser(String username, Long targetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 관리자가 아니라면 에러
        if(user.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 회원 정지시 user_status 변경
        target.setUserStatus(Status.SUSPENDED);
        userRepository.save(target);
    }

    @Override
    public void unsuspendUser(String username, Long targetId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 관리자가 아니라면 에러
        if(user.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 회원 정지 해제시 user_status 변경
        target.setUserStatus(Status.ACTIVE);
        userRepository.save(target);
    }

    @Override
    public String getStatistics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 관리자가 아니라면 에러
        if(user.getRole() != Role.ADMIN) {
            throw new UserException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 총 사용자, 활성 사용자, 비활성 사용자, 정지 사용자, 삭제 사용자
        Long countTotalUsers = userRepository.count();
        Long countActiveUsers = userRepository.countByUserStatus(Status.ACTIVE);
        Long countInactiveUsers = userRepository.countByUserStatus(Status.INACTIVE);
        Long countSuspendedUsers = userRepository.countByUserStatus(Status.SUSPENDED);
        Long countDeletedUsers = userRepository.countByUserStatus(Status.DELETED);

        // 최근 한 달 및 일주일 이내 가입자 수
        Long countRegisteredWithinOneMonth = 0L;
        Long countRegisteredWithinOneWeek = 0L;
        List<User> users = userRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);
        LocalDateTime oneWeekAgo = now.minusWeeks(1);

        for (User target : users) {
            LocalDateTime registeredAt = target.getRegisteredAt();
            if (registeredAt.isAfter(oneMonthAgo)) {
                countRegisteredWithinOneMonth++;
                if (registeredAt.isAfter(oneWeekAgo)) {
                    countRegisteredWithinOneWeek++;
                }
            }
        }

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
}
