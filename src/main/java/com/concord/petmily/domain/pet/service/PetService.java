package com.concord.petmily.domain.pet.service;

import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.domain.pet.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PetService {
    /**
     * 반려동물 생성 메소드
     *
     * @param profileImage 프로필 이미지 파일
     */
    void registerPet(String username, PetDto.Create request, MultipartFile profileImage);

    /**
     * 회원의 반려동물 목록 조회
     *
     * @return 페이징된 반려동물 리스트
     */
    Page<Pet> getPetsByUserId(String username, Pageable pages);

    /**
     * 특정 반려동물 상세 정보 조회
     *
     * @return 반려동물 엔티티
     */
    Pet getPetDetail(Long petId, String username);

    /**
     * 반려동물 정보 수정
     */
    void updatePet(Long petId, String username, PetDto.ModifyPet request, MultipartFile profile);

    /**
     * 반려동물 삭제 처리
     */
    void deletePet(Long petId, String username);

//    /**
//     * 반려동물의 전체 산책 기록 조회
//     */
//    void getPetWalks();
}
