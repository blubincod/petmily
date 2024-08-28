package com.concord.petmily.domain.vaccination.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.exception.PetNotFoundException;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.vaccination.dto.VaccinationDto;
import com.concord.petmily.domain.vaccination.entity.Vaccination;
import com.concord.petmily.domain.vaccination.repository.VaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 접종 비즈니스 로직
 */

@Service
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

    private final PetRepository petRepository;
    private final VaccinationRepository vaccinationRepository;

    @Override
    @Transactional
    public VaccinationDto registerVaccination(Long petId, VaccinationDto vaccinationDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));

        Vaccination vaccination = new Vaccination();
        vaccination.setPet(pet);
        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return convertToVaccinationDto(savedVaccination);
    }

    @Override
    public List<VaccinationDto> getVaccinationsByPetId(Long petId) {
        List<Vaccination> vaccinations = vaccinationRepository.findByPetId(petId);
        return vaccinations.stream()
                .map(this::convertToVaccinationDto)
                .collect(Collectors.toList());
    }

    @Override
    public VaccinationDto getVaccinationDetail(Long petId, Long vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));
        return convertToVaccinationDto(vaccination);
    }

    @Override
    @Transactional
    public VaccinationDto updateVaccination(Long petId, Long vaccinationId, VaccinationDto vaccinationDto) {
        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));

        Vaccination updatedVaccination = vaccinationRepository.save(vaccination);
        return convertToVaccinationDto(updatedVaccination);
    }

    @Override
    @Transactional
    public void deleteVaccination(Long petId, Long vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));
        vaccinationRepository.delete(vaccination);
    }

    @Override
    public Page<VaccinationDto> getAllVaccinations(Pageable pageable) {
        Page<Vaccination> vaccinationsPage = vaccinationRepository.findAll(pageable);
        return vaccinationsPage.map(this::convertToVaccinationDto);
    }

    // VaccinationDto로 변경한는 메서드
    private VaccinationDto convertToVaccinationDto(Vaccination vaccination) {

        return new VaccinationDto();
    }
}
