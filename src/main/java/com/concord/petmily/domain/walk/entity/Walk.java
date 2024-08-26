package com.concord.petmily.domain.walk.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "walk")
@EntityListeners(AuditingEntityListener.class)
public class Walk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 산책 아이디

    private double distance; // 산책 거리
    private long duration; // 산책 시간
    private LocalDateTime startTime; // 산책 시작 시간
    private LocalDateTime endTime; // 산책 종료 시간
    private WalkStatus walkStatus; // 산책 진행 상태

    @CreatedDate
    @Column(name = "walk_date", updatable = false)
    private LocalDate walkDate; // 산책 생성일

    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 매핑
    private User user; // 회원 정보

    @OneToMany(mappedBy = "walk", cascade = CascadeType.ALL)
    private List<WalkParticipant> walkParticipants = new ArrayList<>(); // 그룹
}
