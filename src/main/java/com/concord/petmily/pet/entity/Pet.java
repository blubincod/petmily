package com.concord.petmily.pet.entity;

import com.concord.petmily.pet.dto.PetDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "pet")
@Getter
@Setter
@RequiredArgsConstructor
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(nullable = false, length = 50)
  private String brand;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  private int age;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(nullable = false)
  private Boolean isPetsNeuter;

  @Column(nullable = false)
  private double weight;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(length = 255)
  private String image;

  @Column(unique = true, length = 20)
  private String chip;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime modifiedAt;

  private Pet(Long userId, Category category, String brand, LocalDate birthDate, int age,
      String name, Gender gender, Boolean isPetsNeuter, double weight, Status status,
       String chip) {
    this.userId = userId;
    this.category = category;
    this.brand = brand;
    this.birthDate = birthDate;
    this.age = age;
    this.name = name;
    this.gender = gender;
    this.isPetsNeuter = isPetsNeuter;
    this.weight = weight;
    this.status = status;
    this.chip = chip;
  }

  public static Pet from(Long userId, PetDto.Create request) {
    return new Pet(userId, request.getPetsCategory(), request.getPetsBreed(), request.getBirthDate(),
        request.getPetsAge(), request.getPetsName(), request.getPetsGender(), request.isPetsNeuter(),
        request.getPetsWeight(), Status.ACTIVE,
        request.getPetsChip().isEmpty() ? null : request.getPetsChip());
  }
}