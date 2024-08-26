package com.concord.petmily.domain.walk.dto;

import com.concord.petmily.domain.pet.dto.PetDto;
import lombok.Builder;

import java.util.List;

@Builder
public class WalkingPetDto {
    private Long walkId;
    private Long petId;
    private PetDto pet;
    private List<WalkActivityDto> activities;

//    public static WalkingPetDto fromEntity(WalkingPet walkingPet) {
//        return WalkingPetDto.builder()
//                .walkId(walkingPet.getId().getWalkId())
//                .petId(walkingPet.getId().getPetId())
//                .pet(PetDto.fromEntity(walkingPet.getPet()))
//                .activities(walkingPet.getActivities().stream()
//                        .map(WalkActivityDto::fromEntity)
//                        .collect(Collectors.toList()))
//                .build();
//    }
}