package com.concord.petmily.vaccination.repository;

import com.concord.petmily.vaccination.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 반려동물 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface PetRepository extends JpaRepository<Vaccination,Long> {

}
