package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkingPet;
import com.concord.petmily.domain.walk.entity.WalkingPetId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingPetRepository extends JpaRepository<WalkingPet, WalkingPetId> {
    WalkingPet findByWalkIdAndPetId(Long walkId, Long petId);
}
