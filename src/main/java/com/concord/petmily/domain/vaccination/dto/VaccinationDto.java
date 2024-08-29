package com.concord.petmily.domain.vaccination.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationDto {
    private Long id;
    private Long petId;
    private Long diseaseId;
    private String diseaseName;
    private LocalDate vaccinationDate;
    private LocalDate nextVaccinationDate;
    private String clinicName;
}
