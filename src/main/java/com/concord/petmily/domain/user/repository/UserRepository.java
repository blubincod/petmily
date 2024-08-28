package com.concord.petmily.domain.user.repository;

import com.concord.petmily.domain.user.entity.Status;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAllByBirthDate(String birthDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.userStatus = :status")
    Long countByUserStatus(@Param("status") Status status);
}
