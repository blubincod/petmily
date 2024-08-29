package com.concord.petmily.domain.pet.dto;

import com.concord.petmily.domain.pet.entity.PetType;
import com.concord.petmily.domain.pet.entity.Gender;

import java.time.LocalDate;
import javax.validation.constraints.*;

import lombok.*;


public class PetDto {

  @Getter
  @Setter
  public static class Create {
    @NotNull
    private PetType type;

    @NotBlank
    private String breed;

    @NotBlank
    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    @Min(0)
    private int age;

    @NotNull
    private Gender gender;

    @NotNull
    private boolean isNeutered;

    @Min(0)
    private double weight;

    private String imageUrl;

    private String chipNumber;
  }

  @Getter
  @AllArgsConstructor
  public static class PetsVo {
    private Long id;
    private String breed;
    private String name;
    private Gender gender;
    private String imageUrl;
  }

  @Getter
  @Setter
  public static class ModifyPet {
    private String name;
    private LocalDate birthDate;
    private Boolean isNeutered;
    private Double weight;
    private String chipNumber;
  }
}