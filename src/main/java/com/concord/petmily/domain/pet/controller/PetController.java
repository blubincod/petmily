package com.concord.petmily.domain.pet.controller;

import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    /**
     * 반려동물 등록
     */
    @PostMapping
    public ResponseEntity<?> registerPet(
            @RequestParam Long userId,
            @Valid @RequestBody PetDto.Create request,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        petService.createPet(userId, request, profileImage);

        return ResponseEntity.status(HttpStatus.CREATED).body("반려동물 정보 등록 완료");
    }

    /**
     * 회원의 반려동물 목록 조회
     */
    @GetMapping
    public ResponseEntity<Page<Pet>> getUserPets(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable page
    ) {
        Page<Pet> pets = petService.getPetsByUserId(userDetails.getUsername(), page);

        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }

    /**
     * 특정 반려동물 상세 정보 조회
     */
    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetDetail(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long petId
    ) {
        Pet pet = petService.getPetDetail(petId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    /**
     * 반려동물 정보 수정
     */
    @PutMapping("/{petId}")
    public ResponseEntity<?> updatePet(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long petId,
            @RequestBody PetDto.ModifierPet request,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        petService.updatePet(petId, userDetails.getUsername(), request, profileImage);

        return ResponseEntity.ok("반려동물 정보 수정 완료"); // 200 OK 반환
    }

    /**
     * 반려동물 삭제
     */
    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long petId
    ) {
        petService.deletePet(petId, userDetails.getUsername());

        return ResponseEntity.status(204).body("반려 동물 정보 삭제 완료"); // 204 No Content 반환
    }

    // TODO 산책으로 이동
//    /**
//     * 반려동물의 전체 산책 기록 조회
//     */
//    @GetMapping("/{petId}/walks")
//    public ResponseEntity<?> getPetWalks(PathVariable petId) {
//        // TODO 전체 산책 기록 조회 로직
//        return ResponseEntity.ok(null);
//    }
}