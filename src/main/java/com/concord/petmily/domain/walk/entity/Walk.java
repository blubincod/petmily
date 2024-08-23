package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk")
public class Walk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 아이디

    private double distance; // 산책 거리
    private double duration; // 산책 시간
    private LocalDateTime startTime; // 산책 시작 시간
    private LocalDateTime endTime; // 산책 종료 시간
    private WalkStatus status; // 산책 진행 상태

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 매핑
    private User user; // 회원 정보

    @OneToMany(mappedBy = "walk", cascade = CascadeType.ALL)
    private List<WalkingPet> walkingPets = new ArrayList<>(); // 그룹
}
