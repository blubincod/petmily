package com.concord.petmily.domain.disease.entity;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.pet.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disease")
public class Disease {

    //기본 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질병의 이름
    private String name;

    // 질병의 설명
    private String description;
    // 접종 기한
    private Integer vaccinationDeadline;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Category> animalCategories;

    public Disease(String name, String description, Integer vaccinationDeadline, Set<Category> animalCategories) {
        this.name = name;
        this.description = description;
        this.vaccinationDeadline = vaccinationDeadline;
        this.animalCategories = animalCategories;
    }

    public static Disease from(DiseaseDto.Create request) {
        Set<Category> categories = request.getAnimalCategories().stream().map(category -> {
                    try {
                        return Category.valueOf(category.toString().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid category name: " + category);
                    }
                })
                .collect(Collectors.toSet());
        return new Disease(request.getName(), request.getDescription(), request.getVaccinationDeadline(), categories);
    }


}