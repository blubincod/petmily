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
 * 접종 서비스 구현 클래스
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
     * 예방 접종 정보 등록
     *
     * @param username 사용자 이름
     * @param petId    반려동물 ID
     * @param dto      예방 접종 요청 정보
     * @return 등록된 예방 접종 정보
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
     * 특정 반려동물의 모든 예방 접종 정보 조회
     *
     * @param petId 반려동물 ID
     * @return 예방 접종 정보 목록
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
     * 특정 예방 접종의 상세 정보 조회
     *
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
     * @return 예방 접종 상세 정보
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
     * 예방 접종 정보를 수정
     *
     * @param username      사용자 이름
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
     * @param dto           수정할 예방 접종 정보
     * @return 수정된 예방 접종 정보
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
     * 예방 접종 정보 삭제
     *
     * @param username      사용자 이름
     * @param petId         반려동물 ID
     * @param vaccinationId 예방 접종 ID
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
     * 모든 예방 접종 정보 페이지네이션하여 조회 (관리자 기능)
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