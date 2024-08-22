package com.concord.petmily.domain.pet.service;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.entity.Status;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.common.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * 반려동물 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{

  private final PetRepository petRepository;

  @Value("${file.dir}")
  private String fileDir;

  /**
   * 반려동물 생성 메소드
   */
  @Transactional(rollbackFor = Exception.class)
  public void createPet(Long userId, PetDto.Create request, MultipartFile profileImage) {
    // 새로운 반려동물 엔티티를 저장
    Pet petEntity = petRepository.save(Pet.from(userId, request));
    // 프로필 이미지를 저장하고 파일 경로를 반환
    String savedFilePath = saveProfile(userId, petEntity.getId(), profileImage);
    request.setPetsImage(savedFilePath);

    try {
      // 저장된 프로필 경로를 반영하여 다시 저장
      petRepository.save(Pet.from(userId, request));
    } catch (Exception e) {
      // 만약 저장에 실패하면, 업로드된 파일을 삭제
      if (savedFilePath != null) {
        File picFile = new File(FileUtils.getAbsolutePath(fileDir), savedFilePath);
        if (picFile.exists() && !picFile.delete()) {
          // 파일 삭제 실패 처리 (로그 기록 또는 예외 발생)
          throw new RuntimeException("파일 삭제 중 오류 발생: " + picFile.getAbsolutePath());
        }
      }
      throw e;  // 원본 예외를 다시 던져 트랜잭션 롤백 유도
    }
  }

  /**
   * 프로필 이미지 저장 메소드
   */
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
   * 사용자별 반려동물 리스트 조회
   */
  public Page<Pet> userPetList(Long userId, Pageable pages) {
    // 사용자 ID와 상태를 기준으로 활성화된 반려동물 리스트 조회
    return petRepository.findByUserIdAndStatus(userId, Status.ACTIVE, pages);
  }

  /**
   * 특정 반려동물 상세 조회

   */
  public Pet userPetDetail(Long petId, Long userId) {
    // 사용자 ID와 반려동물 ID로 반려동물 정보 조회, 없을 경우 예외 발생
    return petRepository.findByIdAndUserId(petId, userId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물 입니다."));
  }

  /**
   * 반려동물 정보 수정
   */
  @Transactional(rollbackFor = Exception.class)
  public void modifierPet(Long petId, Long userId, PetDto.ModifierPet request,
      MultipartFile profile) {
    // 사용자 ID와 반려동물 ID로 반려동물 정보 조회, 없을 경우 예외 발생
    Pet petEntity = petRepository.findByIdAndUserId(petId, userId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물 입니다."));

    // 반려동물 이름 수정
    if (request.getPetsName() != null) {
      petEntity.setName(request.getPetsName());
    }

    // 중성화 여부 수정
    if (request.getIsPetsNeuter() != null) {
      petEntity.setIsPetsNeuter(request.getIsPetsNeuter());
    }

    // 생년월일 수정
    if (request.getBirthDate() != null) {
      petEntity.setBirthDate(request.getBirthDate());
    }

    // 체중 수정
    if (request.getPetsWeight() != null) {
      petEntity.setWeight(request.getPetsWeight());
    }

    // 새로운 프로필 이미지가 업로드된 경우 처리
    if (!profile.isEmpty()) {
      // 기존 프로필 이미지 삭제
      if (petEntity.getImage() != null) {
        File file = new File(FileUtils.getAbsolutePath(fileDir), petEntity.getImage());
        if (file.exists() && !file.delete()) {
          // 예외 발생: 파일 삭제 실패
          throw new RuntimeException("기존 프로필 파일 삭제에 실패했습니다.");
        }
      }

      // 새로운 프로필 이미지 저장
      String saveProfilePath = saveProfile(userId, petEntity.getId(), profile);
      petEntity.setImage(saveProfilePath);  // 새 이미지 경로 설정
    }

    // 마이크로칩 수정
    if (request.getPetsChip() != null) {
      petEntity.setChip(request.getPetsChip());
    }
  }

  /**
   * 반려동물 삭제 처리
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePet(Long petId, Long userId) {
    // 사용자 ID와 반려동물 ID로 반려동물 정보 조회, 없을 경우 예외 발생
    Pet petEntity = petRepository.findByIdAndUserId(petId, userId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 반려동물입니다."));

    // 반려동물에 프로필 이미지가 있는 경우 파일 삭제
    if (petEntity.getImage() != null) {
      File file = new File(FileUtils.getAbsolutePath(fileDir), petEntity.getImage());
      if (file.exists() && !file.delete()) {
        // 예외 발생: 파일 삭제 실패
        throw new RuntimeException("프로필 파일 삭제에 실패했습니다.");
      }
    }

    // 반려동물 상태를 'DELETED'로 설정하여 논리적 삭제
    petEntity.setStatus(Status.DELETED);
  }

  /**
   * 반려동물의 전체 산책 기록 조회
   */
  @Override
  public void getPetWalks() {

  }





}