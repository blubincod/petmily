package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 산책 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
public interface WalkRepository extends JpaRepository<Walk, Long> {

}
