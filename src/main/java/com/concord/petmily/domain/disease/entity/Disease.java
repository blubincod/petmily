package com.concord.petmily.domain.disease.entity;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.pet.entity.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer vaccinationCycle; // 예방 접종 주기

    @Enumerated(EnumType.STRING)
    private PetType petType;

    public static Disease fromDto(DiseaseDto.Create dto) {
        Disease disease = new Disease();
        disease.setName(dto.getName());
        disease.setDescription(dto.getDescription());
        disease.setVaccinationCycle(dto.getVaccinationCycle());
        disease.setPetType(dto.getPetType());
        return disease;
    }

    // 다음 접종일을 계산하는 메서드
    public LocalDate calculateNextDueDate(LocalDate vaccinationDate) {
        return vaccinationDate.plusDays(this.getVaccinationCycle());
    }
}