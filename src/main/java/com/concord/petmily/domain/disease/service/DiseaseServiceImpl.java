package com.concord.petmily.domain.disease.service;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.disease.repository.DiseaseRepository;
import com.concord.petmily.domain.pet.entity.PetType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 질병 로직
 */
@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public void createDisease(DiseaseDto.Create request) {
        if (diseaseRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("이미 등록된 질병입니다.");
        }

        Disease disease = Disease.fromDto(request);
        diseaseRepository.save(disease);
    }

    public Page<Disease> getDiseases(String name, String categoryName, Pageable pageable) {
        PetType petType = null;

        if (StringUtils.hasText(categoryName)) {
            petType = PetType.fromString(categoryName);
        }

        if (StringUtils.hasText(name) && petType != null) {
            return diseaseRepository.findByNameContainingIgnoreCaseAndPetType(name, petType, pageable);
        } else if (StringUtils.hasText(name)) {
            return diseaseRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (petType != null) {
            return diseaseRepository.findByPetType(petType, pageable);
        } else {
            return diseaseRepository.findAll(pageable);
        }
    }

    public Disease getDiseaseDetail(Long diseaseId) {
        return diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));
    }

    public Disease partialUpdateDisease(Long diseaseId, DiseaseDto.Modifier request) {
        Disease disease = diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));

        if (StringUtils.hasText(request.getName())) {
            disease.setName(request.getName());
        }
        if (StringUtils.hasText(request.getDescription())) {
            disease.setDescription(request.getDescription());
        }
        if (request.getVaccinationDeadline() != null) {
            disease.setVaccinationDeadline(request.getVaccinationDeadline());
        }
        if (request.getPetType() != null) {
            disease.setPetType(request.getPetType());
        }

        return diseaseRepository.save(disease);
    }

    public void deleteDisease(Long diseaseId) {
        Disease disease = diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));

        diseaseRepository.delete(disease);
    }
}
