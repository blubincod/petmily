package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 산책 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
public interface WalkActivityRepository extends JpaRepository<WalkActivity, Long> {

    // 산책 아이디로 특정 산책 활동 기록 찾기
    @Query("SELECT wp FROM WalkActivity wp WHERE wp.walkParticipant.walk.id = :walkId")
    List<WalkActivity> findByWalkId(@Param("walkId") Long walkId);
}
