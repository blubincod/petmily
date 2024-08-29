package com.concord.petmily.domain.vaccination.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.disease.entity.Disease;
import com.concord.petmily.domain.disease.exception.DiseaseNotFoundException;
import com.concord.petmily.domain.disease.repository.DiseaseRepository;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.exception.PetException;
import com.concord.petmily.domain.pet.exception.PetNotFoundException;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import com.concord.petmily.domain.vaccination.dto.VaccinationDto;
import com.concord.petmily.domain.vaccination.dto.VaccinationRequestDto;
import com.concord.petmily.domain.vaccination.entity.Vaccination;
import com.concord.petmily.domain.vaccination.repository.VaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 접종 비즈니스 로직
 */

@Service
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

    private final PetRepository petRepository;
    private final VaccinationRepository vaccinationRepository;
    private final DiseaseRepository diseaseRepository;
    private final UserRepository userRepository;

    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 예방 접종 정보를 등록합니다.
     *
     * @param username 사용자 이름
     * @param petId    반려동물 ID
     * @param dto      예방 접종 요청 정보
     * @return 등록된 예방 접종 정보
     * @throws UserNotFoundException    사용자를 찾을 수 없는 경우
     * @throws PetNotFoundException     반려동물을 찾을 수 없는 경우
     * @throws PetException             반려동물 소유자가 일치하지 않는 경우
     * @throws DiseaseNotFoundException 질병을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public VaccinationDto registerVaccination(String username, Long petId, VaccinationRequestDto dto) {
        User user = getUser(username);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));
        Disease disease = diseaseRepository.findById(dto.getDiseaseId())
                .orElseThrow(() -> new DiseaseNotFoundException(ErrorCode.DISEASE_NOT_FOUND));

        // 회원의 반려동물 여부 확인
        if (pet.getUser() != user) {
            throw (new PetException(ErrorCode.PET_OWNER_MISMATCH));
        }

        // 질병이 해당 반려동물 관련 질병인지 확인
        if (pet.getType() != disease.getPetType()) {
            throw new RuntimeException("해당 반려둥물에 해당하는 질병이 아닙니다.");
        }

        // 해당 반려동물에 대한 해당 질병의 예방접종 정보 있는지 확인
        Optional<Vaccination> existingVaccination = vaccinationRepository.findByPetIdAndDiseaseId(petId, disease.getId());
        if (existingVaccination.isPresent()) {
            throw new RuntimeException("이미 해당 반려동물에 대한 해당 질병의 예방접종 정보가 존재합니다.");
        }

        Vaccination vaccination = new Vaccination();
        vaccination.setPet(pet);
        vaccination.setDisease(disease);
        vaccination.setClinicName(dto.getClinicName());
        vaccination.setVaccinationDate(dto.getVaccinationDate());
        vaccination.setNextVaccinationDate(disease.calculateNextDueDate(dto.getVaccinationDate()));

        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return savedVaccination.toVaccinationDto();
    }

    /**
     * 특정 반려동물의 모든 예방 접종 정보를 조회합니다.
     *
     * @param petId 반려동물 ID
     * @return 예방 접종 정보 목록
     * @throws PetNotFoundException 반려동물을 찾을 수 없는 경우
     */
    @Override
    public List<VaccinationDto> getVaccinationsByPetId(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));

        List<Vaccination> vaccinations = vaccinationRepository.findByPetId(petId);
        return vaccinations.stream()
                .map(Vaccination::toVaccinationDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 예방 접종의 상세 정보를 조회합니다.
     *
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
     * @return 예방 접종 상세 정보
     * @throws PetNotFoundException 반려동물을 찾을 수 없는 경우
     * @throws RuntimeException     예방 접종 기록을 찾을 수 없는 경우
     */
    @Override
    public VaccinationDto getVaccinationDetail(Long petId, Long vaccinationId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));

        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("해당 예방 접종 기록을 찾을 수 없습니다."));
        return vaccination.toVaccinationDto();
    }

    /**
     * 예방 접종 정보를 수정합니다.
     *
     * @param username      사용자 이름
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
     * @param dto           수정할 예방 접종 정보
     * @return 수정된 예방 접종 정보
     * @throws UserNotFoundException    사용자를 찾을 수 없는 경우
     * @throws PetNotFoundException     반려동물을 찾을 수 없는 경우
     * @throws PetException             반려동물 소유자가 일치하지 않는 경우
     * @throws DiseaseNotFoundException 질병을 찾을 수 없는 경우
     * @throws RuntimeException         예방 접종 기록을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public VaccinationDto updateVaccination(String username, Long petId, Long vaccinationId, VaccinationRequestDto dto) {
        User user = getUser(username);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));

        if (pet.getUser() != user) {
            throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
        }

        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));

        Disease disease = diseaseRepository.findById(dto.getDiseaseId())
                .orElseThrow(() -> new DiseaseNotFoundException(ErrorCode.DISEASE_NOT_FOUND));

        if (pet.getType() != disease.getPetType()) {
            throw new RuntimeException("해당 반려동물에 해당하는 질병이 아닙니다.");
        }

        vaccination.setDisease(disease);
        vaccination.setClinicName(dto.getClinicName());

        // 접종일 업데이트
        if (dto.getVaccinationDate() != null) {
            vaccination.setVaccinationDate(dto.getVaccinationDate());

            // 사용자가 다음 접종일을 직접 지정하지 않은 경우에만 자동 계산
            if (dto.getNextVaccinationDate() == null) {
                vaccination.setNextVaccinationDate(
                        disease.calculateNextDueDate(vaccination.getVaccinationDate()));
            }
        }

        // 사용자가 다음 접종일을 직접 지정한 경우
        if (dto.getNextVaccinationDate() != null) {
            vaccination.setNextVaccinationDate(dto.getNextVaccinationDate());
        }

        Vaccination updatedVaccination = vaccinationRepository.save(vaccination);
        return updatedVaccination.toVaccinationDto();
    }

    /**
     * 예방 접종 정보를 삭제합니다.
     *
     * @param username      사용자 이름
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     * @throws PetNotFoundException  반려동물을 찾을 수 없는 경우
     * @throws PetException          반려동물 소유자가 일치하지 않는 경우
     * @throws RuntimeException      예방 접종 기록을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public void deleteVaccination(String username, Long petId, Long vaccinationId) {
        User user = getUser(username);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(ErrorCode.PET_NOT_FOUND));

        if (pet.getUser() != user) {
            throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
        }

        Vaccination vaccination = vaccinationRepository.findByIdAndPetId(vaccinationId, petId)
                .orElseThrow(() -> new RuntimeException("Vaccination not found"));
        vaccinationRepository.delete(vaccination);
    }

    /**
     * 모든 예방 접종 정보를 페이지네이션하여 조회합니다.
     *
     * @param pageable 페이지 정보
     * @return 페이지네이션된 예방 접종 정보
     */
    @Override
    public Page<VaccinationDto> getAllVaccinations(Pageable pageable) {
        Page<Vaccination> vaccinationsPage = vaccinationRepository.findAll(pageable);
        return vaccinationsPage.map(Vaccination::toVaccinationDto);
    }
}