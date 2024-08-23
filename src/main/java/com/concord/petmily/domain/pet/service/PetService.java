package com.concord.petmily.domain.pet.service;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.dto.PetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {
    /**
     * 반려동물 생성 메소드
     *
     * @param userId 사용자 ID
     * @param request 반려동물 생성 요청 DTO
     * @param profileImage 프로필 이미지 파일
     */
    void createPet(Long userId, PetDto.Create request, MultipartFile profileImage);

    /**
     * 사용자별 반려동물 리스트 조회
     *
     * @param userId 사용자 ID
     * @param pages 페이지 요청 정보
     * @return 페이징된 반려동물 리스트
     */
    Page<Pet> userPetList(Long userId, Pageable pages);

    /**
     * 특정 반려동물 상세 조회
     *
     * @param petId 반려동물 ID
     * @param userId 사용자 ID
     * @return 반려동물 엔티티
     */
    Pet userPetDetail(Long petId, Long userId);

    /**
     * 반려동물 정보 수정
     *
     * @param petId 반려동물 ID
     * @param userId 사용자 ID
     * @param request 수정 요청 DTO
     * @param profile 새로운 프로필 이미지 파일
     */
    void modifierPet(Long petId, Long userId, PetDto.ModifierPet request, MultipartFile profile);

    /**
     * 반려동물 삭제 처리
     *
     * @param petId 반려동물 ID
     * @param userId 사용자 ID
     */
    void deletePet(Long petId, Long userId);

    /**
     * 반려동물의 전체 산책 기록 조회
     */
    void getPetWalks();
}
