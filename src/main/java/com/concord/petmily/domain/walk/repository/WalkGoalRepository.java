package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkGoalRepository extends JpaRepository<WalkGoal, Long> {
    // petId가 WalkGoal의 ID(FK)

    Optional<WalkGoal> findByPetId(Long petId);

    boolean existsByPetId(Long petId);
}