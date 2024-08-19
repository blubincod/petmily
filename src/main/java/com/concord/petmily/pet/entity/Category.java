package com.concord.petmily.pet.entity;

public enum Category {
    DOG, CAT, OTHER;
    //문자를 열거형으로 변환시키는 매소드
    public static Category fromString(String categoryName) {
        try {
            return Category.valueOf(categoryName.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Invalid category name: " + categoryName);
        }
    }

}
