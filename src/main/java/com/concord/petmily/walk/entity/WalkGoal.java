package com.concord.petmily.walk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk_goal")
public class WalkGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 목표 아이디

}
