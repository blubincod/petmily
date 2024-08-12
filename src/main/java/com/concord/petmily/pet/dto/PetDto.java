package com.concord.petmily.pet.dto;

import java.util.Date;
import lombok.Data;


public class PetDto {

  @Data
  public static class Create {
    private Long userId;
    private Long petsCategory;
    private String petsBreed;
    private String petsName;
    private Date birthDate;
    private int petsAge;
    private String petsGender;
    private boolean isPetsNeuter;
    private double petsWeight;
    private String petsImage;
    private int petsChip;
  }

  public enum Category {
    DOG, CAT, OTHER;
  }

  public enum Gender{
    M,F;
  }

  public enum Status{
    ACTIVE,DELETED;
  }
}