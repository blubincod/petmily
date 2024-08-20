package com.concord.petmily.domain.pet.repository;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.entity.Status;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 반려동물 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {

  Page<Pet>findByUserIdAndStatus(Long userId, Status status, Pageable pageable);
  Optional<Pet> findByIdAndUserId(Long id,Long userId);
}
