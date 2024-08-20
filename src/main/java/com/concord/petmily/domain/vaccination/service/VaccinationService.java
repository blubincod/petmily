package com.concord.petmily.domain.vaccination.service;

public interface VaccinationService {

    /**
     * 접종 등록
     */
    void createPet();

    /**
     * 접종 조회
     */
    void userPetList();

    /**
     * 접종 정보 수정
     */
    void modifierPet();

    /**
     * 접종 정보 삭제 처리
     */
    void deletePet();
}
