package com.concord.petmily.pet.entity;

import com.concord.petmily.pet.dto.PetDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "pet")
@RequiredArgsConstructor
public class Pet {

  // 반려동물의 기본 키
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 사용자의 ID
  private Long userId;

  // PetDto의 Category 타입
  private PetDto.Category category;

  // 반려동물의 품종
  private String brand;

  // 반려동물의 생년월일
  private LocalDate birthDate;

  // 반려동물의 나이
  private int age;

  // 반려동물의 이름
  private String name;

  // 반려동물의 Gender 타입
  private PetDto.Gender gender;

  // 반려동물의 중성화 여부
  private boolean isPetsNeuter;

  // 반려동물의 무게
  private int weight;

  // 반려동물의 Status 타입
  private PetDto.Status status;

  // 반려동물의 이미지
  private String image;

  // 반려동물의 칩 번호
  private String chip;

  // 생성 시점
  private LocalDateTime createAt;

  // 수정 시점
  private LocalDateTime modifiedAt;
}