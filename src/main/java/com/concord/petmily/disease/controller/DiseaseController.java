package com.concord.petmily.disease.controller;

import com.concord.petmily.disease.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/disease")
@RequiredArgsConstructor
public class DiseaseController {

  private final DiseaseService diseaseService;

  // 질병 생성 엔드포인트
  @PostMapping()
  public ResponseEntity<?> createDisease() {
    // 질병 생성 로직을 호출합니다.
    // 현재는 성공 상태와 함께 간단한 응답 문자열을 반환합니다.
    return ResponseEntity.status(201).body("ok");
  }

  // 모든 질병 조회 엔드포인트
  @GetMapping()
  public ResponseEntity<?> petDisease() {
    // 모든 질병 조회 로직을 호출합니다.
    // 현재는 간단한 응답 문자열을 반환합니다.
    return ResponseEntity.ok("ok");
  }

  // 특정 질병 상세 정보 조회 엔드포인트
  @GetMapping("/{diseaseId}")
  public ResponseEntity<?> DiseaseDetail(@PathVariable Long diseaseId) {
    // 주어진 질병 ID로 질병 상세 정보 조회 로직을 호출합니다.
    // 현재는 간단한 응답 문자열을 반환합니다.
    return ResponseEntity.ok("ok");
  }

  // 특정 질병 수정 엔드포인트
  @PutMapping("/{diseaseId}")
  public ResponseEntity<?> modifierDisease(@PathVariable Long diseaseId) {
    // 주어진 질병 ID로 질병 수정 로직을 호출합니다.
    // 현재는 간단한 응답 문자열을 반환합니다.
    return ResponseEntity.ok("ok");
  }

  // 특정 질병 삭제 엔드포인트
  @DeleteMapping("/{diseaseId}")
  public ResponseEntity<?> deletePetVaccination(@PathVariable Long diseaseId) {
    // 주어진 질병 ID로 질병 삭제 로직을 호출합니다.
    // 현재는 간단한 응답 문자열을 반환합니다.
    return ResponseEntity.ok("ok");
  }
}