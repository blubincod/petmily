package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface WalkGoalRepository extends JpaRepository<WalkGoal, Long> {
    // petId가 WalkGoal의 ID(FK)

    Optional<WalkGoal> findByPetId(Long petId);

    boolean existsByPetId(Long petId);

    // dailyTargetMinutes와 targetStartTime을 합산하여 주어진 시간 범위 사이인 WalkGoal 찾음
    @Query(
            value = "SELECT * FROM walk_goal wg " +
                    "WHERE (wg.target_start_time + INTERVAL '1 minute' * wg.daily_target_minutes) " +
                    "BETWEEN :startTime AND :endTime",
            nativeQuery = true
    )
    List<WalkGoal> findByDailyTargetMinutesAndTargetStartTime(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

    // targetStartTime이 주어진 시간 범위 사이인 WalkGoal 찾음
    @Query("SELECT wg FROM WalkGoal wg WHERE wg.targetStartTime BETWEEN :startTime AND :endTime")
    List<WalkGoal> findByTargetStartTimeBetween(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}