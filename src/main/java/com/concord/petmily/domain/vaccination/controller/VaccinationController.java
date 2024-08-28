package com.concord.petmily.domain.vaccination.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.common.dto.PagedApiResponse;
import com.concord.petmily.domain.vaccination.dto.VaccinationDto;
import com.concord.petmily.domain.vaccination.service.VaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 반려동물의 접종 정보 관련 컨트롤러
 * - 예방 접종 정보 등록
 * - 특정 반려동물의 예방 접종 목록 조회
 * - 특정 예방 접종의 상세 정보 조회
 * - 특정 예방 접종 정보 수정
 * - 특정 예방 접종 정보 삭제
 * - 전체 접종 정보 조회 (관리자 기능)
 * <p>
 * 이 컨트롤러는 반려동물의 예방 접종 정보를 관리하는 API 엔드포인트를 제공합니다.
 * 각 엔드포인트는 특정 반려동물에 대한 접종 정보를 처리하며,
 * 관리자를 위한 전체 접종 정보 조회 기능도 포함하고 있습니다.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    // 예방 접종 정보 등록
    @PostMapping("/pets/{petId}/vaccinations")
    public ResponseEntity<ApiResponse<VaccinationDto>> registerPetVaccination(@PathVariable Long petId, @RequestBody VaccinationDto vaccinationDto) {
        VaccinationDto registeredVaccination = vaccinationService.registerVaccination(petId, vaccinationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(registeredVaccination));
    }

    // 특정 반려동물의 예방 접종 목록 조회
    @GetMapping("/pets/{petId}/vaccinations")
    public ResponseEntity<ApiResponse<List<VaccinationDto>>> getPetVaccinations(@PathVariable Long petId) {
        List<VaccinationDto> vaccinations = vaccinationService.getVaccinationsByPetId(petId);
        return ResponseEntity.ok(ApiResponse.success(vaccinations));
    }

    // 특정 예방 접종의 상세 정보 조회
    @GetMapping("/pets/{petId}/vaccinations/{vaccinationId}")
    public ResponseEntity<ApiResponse<VaccinationDto>> getPetVaccinationDetail(@PathVariable Long petId, @PathVariable Long vaccinationId) {
        VaccinationDto vaccination = vaccinationService.getVaccinationDetail(petId, vaccinationId);
        return ResponseEntity.ok(ApiResponse.success(vaccination));
    }

    // 특정 예방 접종 수정
    @PutMapping("/pets/{petId}/vaccinations/{vaccinationId}")
    public ResponseEntity<ApiResponse<VaccinationDto>> updatePetVaccination(@PathVariable Long petId, @PathVariable Long vaccinationId, @RequestBody VaccinationDto vaccinationDto) {
        VaccinationDto updatedVaccination = vaccinationService.updateVaccination(petId, vaccinationId, vaccinationDto);
        return ResponseEntity.ok(ApiResponse.success(updatedVaccination));
    }

    // 특정 예방 접종 삭제
    @DeleteMapping("/pets/{petId}/vaccinations/{vaccinationId}")
    public ResponseEntity<ApiResponse<Void>> deletePetVaccination(@PathVariable Long petId, @PathVariable Long vaccinationId) {
        vaccinationService.deleteVaccination(petId, vaccinationId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    // 전체 접종 조회 (관리자)
    @GetMapping("/vaccinations")
    public ResponseEntity<PagedApiResponse<List<VaccinationDto>>> getAllVaccinations(Pageable pageable) {
        Page<VaccinationDto> vaccinationsPage = vaccinationService.getAllVaccinations(pageable);
        return ResponseEntity.ok(PagedApiResponse.success(vaccinationsPage));
    }
}