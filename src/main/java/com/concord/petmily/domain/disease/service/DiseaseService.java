package com.concord.petmily.domain.disease.service;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.disease.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiseaseService {

    void createDisease(DiseaseDto.Create request);

    Page<Disease> getDiseases(String name, String categoryName, Pageable pageable);

    Disease getDiseaseDetail(Long diseaseId);

    Disease partialUpdateDisease(Long diseaseId, DiseaseDto.Modifier request);

    void deleteDisease(Long diseaseId);
}
