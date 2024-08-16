package com.concord.petmily.disease.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "disease")
@RequiredArgsConstructor
public class Disease {

  //기본 키
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 질병의 이름
  private String name;

  // 질병의 설명
  private String description;
}