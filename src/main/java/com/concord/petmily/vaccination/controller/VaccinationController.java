package com.concord.petmily.vaccination.controller;

import com.concord.petmily.vaccination.service.VaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

  private final VaccinationService vaccinationService;

  @PostMapping
  public ResponseEntity<?> createPet() {
    vaccinationService.createPet();

    return ResponseEntity.status(201).body("ok");
  }

  /**
   * 회원이 소유하고있는 반려동물들을 모두 보여준다
   *
   * @return
   */
  @GetMapping
  public ResponseEntity<?> userPetList() {
    vaccinationService.userPetList();

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
    vaccinationService.userPetList();

    return ResponseEntity.status(201).body("ok");
  }

  @PutMapping
  public ResponseEntity<?> modifierPet() {
    vaccinationService.modifierPet();

    return ResponseEntity.status(201).body("ok");
  }

  @DeleteMapping
  public ResponseEntity<?> deletePat() {
    vaccinationService.deletePet();

    return ResponseEntity.status(204).body("ok");
  }

  @PostMapping("/{petId}/vaccination")
  public ResponseEntity<?> createVaccination(@PathVariable Long petId) {

    return ResponseEntity.status(201).body("ok");
  }

  @GetMapping("/{petId}/vaccination")
  public ResponseEntity<?> petVaccination(@PathVariable Long petId) {

    return ResponseEntity.ok("ok");
  }

  @GetMapping("/{petId}/vaccination/{vaccinationId}")
  public ResponseEntity<?> petVaccinationDetail(@PathVariable Long petId,
      @PathVariable Long vaccinationId) {

    return ResponseEntity.ok("ok");
  }

  @PutMapping("/{petId}/vaccination/{vaccinationId}")
  public ResponseEntity<?> modifierPetVaccination(@PathVariable Long petId,
      @PathVariable Long vaccinationId) {

    return ResponseEntity.ok("ok");
  }

  @DeleteMapping("/{petId}/vaccination/{vaccinationId}")
  public ResponseEntity<?> deletePetVaccination(@PathVariable Long petId,
      @PathVariable Long vaccinationId) {

    return ResponseEntity.ok("ok");
  }


}