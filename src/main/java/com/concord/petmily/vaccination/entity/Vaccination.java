package com.concord.petmily.vaccination.entity;

import com.concord.petmily.disease.entity.Disease;
import com.concord.petmily.pet.entity.Pet;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "vaccination")
@RequiredArgsConstructor
public class Vaccination {

  // 기본 키
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //하나의 반려동물과 연결.
  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet petId;

  // 하나의 질병과 연결.
  @ManyToOne
  @JoinColumn(name = "disease_id")
  private Disease diseaseId;

  // 예방 접종 날짜
  private LocalDate vaccinationDate;

  // 예방 접종 여부
  private boolean isVaccination;

  // 다음 예정일
  private LocalDate nextDueDate;

  // 생성 시점
  private LocalDateTime createAt;

  // 수정 시점
  private LocalDateTime modifiedAt;
}