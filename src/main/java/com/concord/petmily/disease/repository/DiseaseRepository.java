package com.concord.petmily.disease.repository;

import com.concord.petmily.disease.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 질병 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface DiseaseRepository extends JpaRepository<Disease,Long> {

}
