package com.concord.petmily.domain.vaccination.controller;

import com.concord.petmily.domain.vaccination.service.VaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    // 예방 접종 생성 엔드포인트
    @PostMapping("/{petId}/vaccination")
    public ResponseEntity<?> createVaccination(@PathVariable Long petId) {
        // 예방 접종 생성 로직을 호출합니다.
        // 현재는 성공 상태와 함께 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.status(201).body("ok");
    }

    // 특정 반려동물의 예방 접종 조회 엔드포인트
    @GetMapping("/{petId}/vaccination")
    public ResponseEntity<?> petVaccination(@PathVariable Long petId) {
        // 주어진 반려동물 ID로 예방 접종 조회 로직을 호출합니다.
        // 현재는 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.ok("ok");
    }

    // 특정 예방 접종의 상세 정보 조회 엔드포인트
    @GetMapping("/{petId}/vaccination/{vaccinationId}")
    public ResponseEntity<?> petVaccinationDetail(@PathVariable Long petId,
                                                  @PathVariable Long vaccinationId) {
        // 주어진 예방 접종 ID로 예방 접종 상세 정보 조회 로직을 호출합니다.
        // 현재는 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.ok("ok");
    }

    // 특정 예방 접종 수정 엔드포인트
    @PutMapping("/{petId}/vaccination/{vaccinationId}")
    public ResponseEntity<?> modifierPetVaccination(@PathVariable Long petId,
                                                    @PathVariable Long vaccinationId) {
        // 주어진 예방 접종 ID로 예방 접종 수정 로직을 호출합니다.
        // 현재는 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.ok("ok");
    }

    // 특정 예방 접종 삭제 엔드포인트
    @DeleteMapping("/{petId}/vaccination/{vaccinationId}")
    public ResponseEntity<?> deletePetVaccination(@PathVariable Long petId,
                                                  @PathVariable Long vaccinationId) {
        // 주어진 예방 접종 ID로 예방 접종 삭제 로직을 호출합니다.
        // 현재는 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.ok("ok");
    }
}