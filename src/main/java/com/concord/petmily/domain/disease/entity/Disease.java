package com.concord.petmily.domain.disease.entity;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.pet.entity.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer vaccinationDeadline;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    public static Disease fromDto(DiseaseDto.Create dto) {
        Disease disease = new Disease();
        disease.setName(dto.getName());
        disease.setDescription(dto.getDescription());
        disease.setVaccinationDeadline(dto.getVaccinationDeadline());
        disease.setPetType(dto.getPetType());
        return disease;
    }
}