package com.concord.petmily.pet.dto;

import com.concord.petmily.pet.entity.Category;
import com.concord.petmily.pet.entity.Gender;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;


public class PetDto {

  @Data
  public static class Create {
    private Long userId;
    private Category petsCategory;
    private String petsBreed;
    private String petsName;
    private LocalDate birthDate;
    private int petsAge;
    private Gender petsGender;
    private boolean isPetsNeuter;
    private double petsWeight;
    private String petsImage;
    private String  petsChip;
  }






}