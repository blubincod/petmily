package com.concord.petmily.domain.vaccination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRequestDto {
    private Long diseaseId;
    private LocalDate vaccinationDate;
    private LocalDate nextVaccinationDate;
    private String clinicName;
}
