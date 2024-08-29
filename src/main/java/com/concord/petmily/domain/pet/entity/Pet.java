package com.concord.petmily.domain.pet.entity;

import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.walk.entity.WalkParticipant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Pet 엔티티를 로드할 때 User 정보를 즉시 로드하지 않고, 실제로 사용될 때 로드
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(nullable = false, length = 50)
    private String breed;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Boolean isNeutered; // 중성화 여부

    @Column(nullable = false)
    private double weight;

    @Column(name = "pet_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    @Column(length = 255)
    private String image;

    @Column(unique = true, length = 20)
    private String chipNumber;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "pet")
    private List<WalkParticipant> walkParticipants;

    public static Pet fromDto(User user, PetDto.Create request) {
        return Pet.builder()
                .user(user)
                .type(request.getType())
                .breed(request.getBreed())
                .birthDate(request.getBirthDate())
                .age(request.getAge())
                .name(request.getName())
                .gender(request.getGender())
                .isNeutered(request.isNeutered())
                .weight(request.getWeight())
                .status(PetStatus.ACTIVE)
                .image(request.getImageUrl())
                .chipNumber(request.getChipNumber() != null && !request.getChipNumber().isEmpty() ? request.getChipNumber() : null)
                .build();
    }
}