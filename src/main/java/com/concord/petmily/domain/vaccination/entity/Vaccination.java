package com.concord.petmily.domain.vaccination.entity;

import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "vaccination")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //한마리의 반려동물과 연결
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // 하나의 질병과 연결
    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    // 예방 접종 날짜
    private LocalDate vaccinationDate;

    // 예방 접종 여부
    private boolean isVaccination;

    // 다음 예정일
    private LocalDate nextDueDate;

    // 생성 시점
    private LocalDateTime createAt;

    // 수정 시점
    private LocalDateTime modifiedAt;
}