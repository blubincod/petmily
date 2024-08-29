package com.concord.petmily.domain.pet.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.common.utils.FileUtils;
import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.entity.PetStatus;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 반려동물 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    // TODO 파일 경로 config 클래스에 정리
    @Value("${file.dir}")
    private String fileDir;

    /**
     * 반려동물 생성 메소드
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerPet(String username, PetDto.Create request, MultipartFile profileImage) {
        User user = getUser(username);
        Pet petEntity = petRepository.save(Pet.fromDto(user, request));
        String savedFilePath = saveProfile(user.getId(), petEntity.getId(), profileImage);
        request.setImageUrl(savedFilePath);

        try {
            petRepository.save(Pet.fromDto(user, request));
        } catch (Exception e) {
            if (savedFilePath != null) {
                File picFile = new File(FileUtils.getAbsolutePath(fileDir), savedFilePath);
                if (picFile.exists() && !picFile.delete()) {
                    throw new RuntimeException("파일 삭제 중 오류 발생: " + picFile.getAbsolutePath());
                }
            }
            throw e;
        }
    }


    // 프로필 이미지 저장 메소드
    private String saveProfile(Long userId, Long petId, MultipartFile profileImage) {
        String savedFilePath = null;

        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 저장 경로 설정
            String basePath = FileUtils.getAbsolutePath(fileDir);
            String centerPath = String.format("pet/%d/%d", userId, petId);
            String dicPath = String.format("%s/%s", basePath, centerPath);
            String savedFileName = FileUtils.makeRandomFileNm(profileImage.getOriginalFilename());
            savedFilePath = String.format("%s/%s", centerPath, savedFileName);
            String targetPath = String.format("%s/%s", basePath, savedFilePath);

            // 디렉토리가 존재하지 않으면 생성
            File dic = new File(dicPath);
            if (!dic.exists()) {
                dic.mkdirs();
            }

            // 파일을 실제 경로로 저장
            File target = new File(targetPath);
            try {
                profileImage.transferTo(target);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 중 오류 발생: " + e.getMessage(), e);
            }
        }
        return savedFilePath;  // 저장된 파일 경로 반환
    }

    /**
     * 회원의 반려동물 목록 조회
     */
    @Override
    public Page<Pet> getPetsByUserId(String username, Pageable pages) {
        User user = getUser(username);

        return petRepository.findByUserIdAndStatus(user.getId(), PetStatus.ACTIVE, pages);
    }

    /**
     * 특정 반려동물 상세 정보 조회
     */
    @Override
    public Pet getPetDetail(Long petId, String username) {
        User user = getUser(username);

        // 사용자 ID와 반려동물 ID로 반려동물 정보 조회, 없을 경우 예외 발생
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물 입니다."));

        return pet;
    }

    /**
     * 반려동물 정보 수정
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePet(Long petId, String username, PetDto.ModifyPet request,
                          MultipartFile profile) {
        User user = getUser(username);
        Pet pet = petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물입니다."));

        if (request.getName() != null) {
            pet.setName(request.getName());
        }
        if (request.getIsNeutered() != null) {
            pet.setIsNeutered(request.getIsNeutered());
        }
        if (request.getBirthDate() != null) {
            pet.setBirthDate(request.getBirthDate());
        }
        if (request.getWeight() != null) {
            pet.setWeight(request.getWeight());
        }

        if (profile != null && !profile.isEmpty()) {
            if (pet.getImage() != null) {
                File file = new File(FileUtils.getAbsolutePath(fileDir), pet.getImage());
                if (file.exists() && !file.delete()) {
                    throw new RuntimeException("기존 프로필 파일 삭제에 실패했습니다.");
                }
            }
            String saveProfilePath = saveProfile(user.getId(), pet.getId(), profile);
            pet.setImage(saveProfilePath);
        }

        if (request.getChipNumber() != null) {
            pet.setChipNumber(request.getChipNumber());
        }
    }

    /**
     * 반려동물 삭제 처리
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePet(Long petId, String username) {
        User user = getUser(username);
        Pet pet = petRepository.findByIdAndUserId(petId, user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물입니다."));

        if (pet.getImage() != null) {
            File file = new File(FileUtils.getAbsolutePath(fileDir), pet.getImage());
            if (file.exists() && !file.delete()) {
                throw new RuntimeException("프로필 파일 삭제에 실패했습니다.");
            }
        }

        pet.setStatus(PetStatus.DELETED);
    }


    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}