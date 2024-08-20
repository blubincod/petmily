package com.concord.petmily.domain.vaccination.service;

import com.concord.petmily.domain.vaccination.repository.VaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  접종 비즈니스 로직
 *
 */
@Service
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

  private final VaccinationRepository vaccinationRepository;

  /**
   * 접종 등록
   */
  public void createPet(){

  }

  /**
   * 접종 조회
   */
  public void userPetList(){

  }

  /**
   * 접종 정보 수정
   */
  public void modifierPet(){

  }

  /**
   * 접종 정보 삭제 처리
   */

  public void deletePet(){

  }

}
