package com.concord.petmily.domain.vaccination.service;

import com.concord.petmily.domain.vaccination.dto.VaccinationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VaccinationService {

    /**
     * 접종 등록
     */
    VaccinationDto registerVaccination(Long petId, VaccinationDto vaccinationDto);

    /**
     * 특정 반려동물의 예방 접종 목록 조회
     */
    List<VaccinationDto> getVaccinationsByPetId(Long petId);

    /**
     * 특정 반려동물의 특정 접종 정보 조회
     */
    VaccinationDto getVaccinationDetail(Long petId, Long vaccinationId);
    /**
     * 특정 반려동물의 접종 정보 수정
     */
    VaccinationDto updateVaccination(Long petId, Long vaccinationId, VaccinationDto vaccinationDto);

    /**
     * 특정 반려동물의 접종 정보 삭제 처리
     */
    void deleteVaccination(Long petId, Long vaccinationId);

    /**
     * 모든 반려동물별 전체 접종 정보 목록
     */
    Page<VaccinationDto> getAllVaccinations(Pageable pageable);
}
