package com.concord.petmily.pet.dto;

import com.concord.petmily.pet.entity.Category;
import com.concord.petmily.pet.entity.Gender;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


public class PetDto {

  @Data
  public static class Create {

    @NotNull(message = "반려동물의 카테고리는 필수입니다.")
    private Category petsCategory;

    @NotBlank(message = "반려동물의 품종은 필수입니다.")
    private String petsBreed;

    @NotBlank(message = "반려동물의 이름은 필수입니다.")
    private String petsName;

    @NotNull(message = "반려동물의 생년월일은 필수입니다.")
    @PastOrPresent(message = "반려동물의 생년월일은 현재 날짜 또는 과거여야 합니다.")
    private LocalDate birthDate;

    @Min(value = 0, message = "반려동물의 나이는 0 이상이어야 합니다.")
    private int petsAge;

    @NotNull(message = "반려동물의 성별은 필수입니다.")
    private Gender petsGender;
    @NotNull(message = "반려동물의 중성화 여부는 필수입니다.")
    private boolean isPetsNeuter;

    @Min(value = 0, message = "반려동물의 무게는 0보다 커야 합니다.")
    private double petsWeight;

    private String petsImage;

    private String petsChip;
  }

  @Getter
  @AllArgsConstructor
  public static class PetsVo{
    private Long petId;
    private String petBrand;
    private String petName;
    private Gender petGender;
    private String petImages;
  }

  @Data
  public static class ModifierPet{

    private String petsName;

    private LocalDate birthDate;

    private Boolean isPetsNeuter;

    private Double petsWeight;

    private String petsChip;
  }
}