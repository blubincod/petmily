package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkingPet;
import com.concord.petmily.domain.walk.entity.WalkingPetId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalkingPetRepository extends JpaRepository<WalkingPet, WalkingPetId> {
    Optional<WalkingPet> findByWalkIdAndPetId(Long walkId, Long petId);

    List<WalkingPet> findByWalkId(Long walkId);

    List<WalkingPet> findPetIdsByWalkId(Long walkId);
}
