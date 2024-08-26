package com.concord.petmily.domain.walk.repository;

import com.concord.petmily.domain.walk.entity.WalkParticipant;
import com.concord.petmily.domain.walk.entity.WalkParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalkParticipantRepository extends JpaRepository<WalkParticipant, WalkParticipantId> {
    Optional<WalkParticipant> findByWalkIdAndPetId(Long walkId, Long petId);

    List<WalkParticipant> findByWalkId(Long walkId);

    List<WalkParticipant> findByIdPetId(Long petId);
}
