package com.concord.petmily.domain.disease.controller;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.disease.service.DiseaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/disease")
@RequiredArgsConstructor
public class DiseaseController {

    private final DiseaseService diseaseService;

    // 질병 생성 엔드포인트
    @PostMapping()
    public ResponseEntity<?> createDisease(@RequestBody DiseaseDto.Create request) {
        // 질병 생성 로직을 호출합니다.
        diseaseService.createDisease(request);
        // 현재는 성공 상태와 함께 간단한 응답 문자열을 반환합니다.
        return ResponseEntity.status(201).body("ok");
    }

    // 모든 질병 조회 엔드포인트
    @GetMapping()
    public ResponseEntity<Page<Disease>> petDisease(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName,
            Pageable pageable) {
        // 서비스 레이어를 통해 질병 목록을 페이징 처리 및 검색하여 가져옴
        Page<Disease> diseases = diseaseService.getDiseases(name, categoryName, pageable);
        return ResponseEntity.ok(diseases);
    }

    // 특정 질병 상세 정보 조회 엔드포인트
    @GetMapping("/{diseaseId}")
    public ResponseEntity<?> diseaseDetail(@PathVariable Long diseaseId) {
        Disease disease = diseaseService.getDiseaseDetail(diseaseId);
        return ResponseEntity.ok(disease);
    }


    // 특정 질병 수정 엔드포인트
    @PutMapping("/diseases/{diseaseId}")
    public ResponseEntity<?> modifierDisease(
            @PathVariable Long diseaseId,
            @Valid @RequestBody DiseaseDto.Modifier request) {

        // 서비스 메서드를 호출하여 질병 정보 업데이트
        Disease updatedDisease = diseaseService.partialUpdateDisease(diseaseId, request);

        // 수정된 질병 정보를 반환
        return ResponseEntity.status(200).body("ok");
    }

    // 특정 질병 삭제 엔드포인트
    @DeleteMapping("/{diseaseId}")
    public ResponseEntity<?> deleteDisease(@PathVariable Long diseaseId) {
        // 서비스 메서드를 호출하여 질병 삭제
        diseaseService.deleteDisease(diseaseId);

        // 성공적으로 삭제되었다는 응답 반환
        return ResponseEntity.status(204).body("질병이 성공적으로 삭제되었습니다.");
    }
}