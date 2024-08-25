package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkingPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 산책 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
public interface WalkRepository extends JpaRepository<Walk, Long> {

    // 특정 날짜의 산책 기록 조회
    List<Walk> findByStartTime(LocalDate date);

     // 반려동물 아이디로 전체 산책 목록 찾기
//    List<Walk> findByPetId(Long petId);

    //
    Page<Walk> findByUserIdAndWalkDateBetween(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    // 회원 아이디로 모든 반려동물의 산책 기록 조회
    Page<Walk> findByUserId(Long userId, Pageable pageable);
}
