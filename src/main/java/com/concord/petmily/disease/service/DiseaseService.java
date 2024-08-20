package com.concord.petmily.disease.service;

import com.concord.petmily.disease.dto.DiseaseDto;
import com.concord.petmily.disease.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiseaseService {

    void createDisease(DiseaseDto.Create request);

    Page<Disease> getDiseases(String name, String categoryName, Pageable pageable);

    Disease getDiseaseDetail(Long diseaseId);

    Disease partialUpdateDisease(Long diseaseId, DiseaseDto.Modifier request);

    void deleteDisease(Long diseaseId);
}
