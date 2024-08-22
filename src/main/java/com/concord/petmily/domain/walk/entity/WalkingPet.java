package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "walking_pet")
public class WalkingPet {

    @EmbeddedId
    private WalkingPetId id;

    @ManyToOne
    @MapsId("petId") // 복합 키 클래스(WalkGroupId)의 필드와 엔티티의 필드를 매핑
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @MapsId("walkId")
    @JoinColumn(name = "walk_id")
    private Walk walk;

}
