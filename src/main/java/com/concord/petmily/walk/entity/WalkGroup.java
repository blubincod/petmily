package com.concord.petmily.walk.entity;

import com.concord.petmily.pet.entity.Pet;
import jakarta.persistence.*;

@Entity
@Table(name = "walk_group")
public class WalkGroup {

    @EmbeddedId
    private WalkGroupId id;

    @ManyToOne
    @MapsId("petId") // 복합 키 클래스(WalkGroupId)의 필드와 엔티티의 필드를 매핑
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @MapsId("walkId")
    @JoinColumn(name = "walk_id")
    private Walk walk;

}
