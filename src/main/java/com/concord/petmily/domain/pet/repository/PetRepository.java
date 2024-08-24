package com.concord.petmily.domain.pet.repository;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.entity.PetStatus;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 반려동물 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {


    // 사용자 ID와 상태를 기준으로 활성화된 반려동물 리스트 조회
    Page<Pet> findByUserIdAndPetStatus(Long userId, PetStatus petStatus, Pageable pageable);

    // 회원의 반려동물 조회
    Optional<Pet> findByIdAndUserId(Long petId, Long userId);

    // 회원의 반려동물 리스트 조회
    List<Pet> findByUser(User user);
}
