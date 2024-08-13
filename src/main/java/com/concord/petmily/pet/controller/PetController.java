package com.concord.petmily.pet.controller;

import com.concord.petmily.pet.dto.PetDto;
import com.concord.petmily.pet.service.PetService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

  private final PetService petService;

  @PostMapping
  public ResponseEntity<?> createPet(@RequestParam Long userId,@RequestBody PetDto.Create request) {
    petService.createPet(userId,request);

    return ResponseEntity.status(201).body("ok");
  }

  /**
   * 회원이 소유하고있는 반려동물들을 모두 보여준다
   *
   * @return
   */
  @GetMapping
  public ResponseEntity<?> userPetList() {
    petService.userPetList();

    return ResponseEntity.status(201).body("ok");
  }

  /**
   * 회원이 소유하고있는 반려동물 상세 페이지
   *
   * @param petId
   * @return
   */
  @GetMapping("/{petId}")
  public ResponseEntity<?> userPetDetail(@PathVariable Long petId) {
    petService.userPetList();

    return ResponseEntity.status(201).body("ok");
  }

  @PutMapping
  public ResponseEntity<?> modifierPet() {
    petService.modifierPet();

    return ResponseEntity.status(201).body("ok");
  }

  @DeleteMapping
  public ResponseEntity<?> deletePat() {
    petService.deletePet();

    return ResponseEntity.status(204).body("ok");
  }


}