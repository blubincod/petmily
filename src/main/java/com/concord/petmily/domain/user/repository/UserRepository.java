package com.concord.petmily.domain.user.repository;

import com.concord.petmily.domain.user.entity.Status;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 회원 조회
    Optional<User> findByUsername(String username);
    // email로 회원 조회
    Optional<User> findByEmail(String email);
    // 회원 중 username이 존재하는지
    boolean existsByUsername(String username);
    // 회원 중 email이 존재하는지
    boolean existsByEmail(String email);
    // birthDate로 회원 조회
    List<User> findAllByBirthDate(String birthDate);
    // 사용자 상태에 따라 사용자 수를 카운트
    Long countByUserStatus(Status status);
    // 특정 날짜 이후 가입된 사용자 수를 카운트
    Long countByRegisteredAtAfter(LocalDateTime dateTime);
}
