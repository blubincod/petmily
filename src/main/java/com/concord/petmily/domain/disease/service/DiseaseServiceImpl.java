package com.concord.petmily.domain.disease.service;

import com.concord.petmily.domain.disease.dto.DiseaseDto;
import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.disease.repository.DiseaseRepository;
import com.concord.petmily.domain.pet.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 질병 로직
 */
@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;

    // TODO 설명을 위한 주석 달기
    public void createDisease(DiseaseDto.Create request) {

        if (diseaseRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("이미 등록된 질병 입니다.");
        }

        Disease diseaseEntity = Disease.from(request);
        diseaseRepository.save(diseaseEntity);
    }

    public Page<Disease> getDiseases(String name, String categoryName, Pageable pageable) {
        Category category = null;

        // 카테고리 이름을 Category enum으로 변환
        if (categoryName != null && !categoryName.isEmpty()) {
            category = Category.fromString(categoryName);
        }

        // 이름과 카테고리로 검색
        if ((name != null && !name.isEmpty()) && category != null) {
            return diseaseRepository.findByNameContainingIgnoreCaseAndAnimalCategoriesIn(name, Set.of(category), pageable);
        }
        // 이름으로만 검색
        else if (name != null && !name.isEmpty()) {
            return diseaseRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        // 카테고리로만 검색
        else if (category != null) {
            return diseaseRepository.findByAnimalCategoriesIn(Set.of(category), pageable);
        }
        // 검색 조건이 없을 경우 모든 질병 반환
        else {
            return diseaseRepository.findAll(pageable);
        }
    }

    // 주어진 질병 ID로 질병을 조회하는 메서드
    public Disease getDiseaseDetail(Long diseaseId) {
        return diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));
    }

    public Disease partialUpdateDisease(Long diseaseId, DiseaseDto.Modifier request) {
        Disease disease = diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));

        // 필드별로 null 체크 및 업데이트
        if (StringUtils.hasText(request.getName())) {
            disease.setName(request.getName());
        }
        if (StringUtils.hasText(request.getDescription())) {
            disease.setDescription(request.getDescription());
        }
        if (request.getVaccinationDeadline() != null) {
            disease.setVaccinationDeadline(request.getVaccinationDeadline());
        }
        if (StringUtils.hasText(request.getAnimalCategory())) {
            disease.setAnimalCategories(Set.of(Category.fromString(request.getAnimalCategory())));
        }

        return diseaseRepository.save(disease);
    }

    public void deleteDisease(Long diseaseId) {
        // 질병이 존재하는지 확인
        Disease disease = diseaseRepository.findById(diseaseId)
                .orElseThrow(() -> new RuntimeException("질병을 찾을 수 없습니다. ID: " + diseaseId));

        // 질병 삭제
        diseaseRepository.delete(disease);
    }


}
