package com.concord.petmily.pet.repository;

import com.concord.petmily.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 반려동물 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {

}
