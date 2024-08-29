package com.concord.petmily.domain.vaccination.entity;

import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.vaccination.dto.VaccinationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "vaccination")
@EntityListeners(AuditingEntityListener.class)
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    private LocalDate vaccinationDate; // 최근 접종일

    private LocalDate nextVaccinationDate; // 다음 접종일

    private String clinicName; // 병원 이름

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public VaccinationDto toVaccinationDto() {
        return VaccinationDto.builder()
                .id(this.id)
                .petId(this.pet != null ? this.pet.getId() : null)
                .diseaseId(this.disease != null ? this.disease.getId() : null)
                .diseaseName(this.disease != null ? this.disease.getName() : null)
                .vaccinationDate(this.vaccinationDate)
                .nextVaccinationDate(this.nextVaccinationDate)
                .clinicName(this.clinicName)
                .build();
    }
}