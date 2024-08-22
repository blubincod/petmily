package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkingPet;
import com.concord.petmily.domain.walk.entity.WalkingPetId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkingPetRepository extends JpaRepository<WalkingPet, WalkingPetId> {

}
