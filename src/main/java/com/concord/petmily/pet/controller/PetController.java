package com.concord.petmily.pet.controller;

import com.concord.petmily.pet.dto.PetDto;
import com.concord.petmily.pet.entity.Pet;
import com.concord.petmily.pet.service.PetService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

  private final PetService petService;

  /**
   * 반려동물 생성
   */
  @PostMapping
  public ResponseEntity<?> createPet(
      @RequestParam Long userId,
      @Valid @RequestBody PetDto.Create request,
      @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
    petService.createPet(userId, request, profileImage);

    return ResponseEntity.status(201).body("반려동물 정보 등록 완료");
  }

  /**
   * 회원이 소유하고 있는 반려동물 목록 조회
   */
  @GetMapping
  public ResponseEntity<Page<Pet>> userPetList(
      @RequestParam Long userId,
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable page) {
    Page<Pet> pets = petService.userPetList(userId, page);

    return ResponseEntity.ok(pets); // 200 OK 반환
  }

  /**
   * 회원이 소유하고 있는 반려동물 상세 조회
   */
  @GetMapping("/{petId}")
  public ResponseEntity<Pet> userPetDetail(
      @PathVariable Long petId,
      @RequestParam Long userId) {
    Pet petDetail = petService.userPetDetail(petId, userId);

    return ResponseEntity.ok(petDetail); // 200 OK 반환
  }

  /**
   * 반려동물 정보 수정
   */
  @PutMapping("/{petId}")
  public ResponseEntity<?> modifierPet(
      @PathVariable Long petId,
      @RequestParam Long userId,
      @RequestBody PetDto.ModifierPet request,
      @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
    petService.modifierPet(petId, userId, request, profileImage);

    return ResponseEntity.ok("반려동물 정보 수정 완료"); // 200 OK 반환
  }

  /**
   * 반려동물 삭제
   */
  @DeleteMapping("/{petId}")
  public ResponseEntity<?> deletePet(
      @PathVariable Long petId,
      @RequestParam Long userId) {
    petService.deletePet(petId, userId);

    return ResponseEntity.status(204).body("반려 동물 정보 삭제 완료"); // 204 No Content 반환
  }
}