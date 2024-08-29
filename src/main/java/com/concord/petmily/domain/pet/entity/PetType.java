package com.concord.petmily.domain.pet.entity;

public enum PetType {
    DOG,
    CAT,
    OTHER;

    //문자를 열거형으로 변환시키는 매서드
    public static PetType fromString(String categoryName) {
        try {
            return PetType.valueOf(categoryName.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Invalid category name: " + categoryName);
        }
    }

}
