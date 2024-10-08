package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk_participant")
public class WalkParticipant {

    @EmbeddedId
    private WalkParticipantId id;

    @ManyToOne
    @MapsId("walkId") // 복합 키 클래스(WalkGroupId)의 필드와 엔티티의 필드를 매핑
    @JoinColumn(name = "walk_id")
    private Walk walk;

    @ManyToOne
    @MapsId("petId")
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "walkParticipant")
    private List<WalkActivity> activities;
}
