package com.concord.petmily.domain.pet.entity;

import com.concord.petmily.domain.pet.dto.PetDto;
import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet")
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, length = 50)
    private String brand;

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
    private Boolean isPetsNeuter;

    @Column(nullable = false)
    private double weight;

    @Column(name = "pet_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;

    @Column(length = 255)
    private String image;

    @Column(unique = true, length = 20)
    private String chip;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public static Pet fromEntity(User user, PetDto.Create request) {
        return Pet.builder()
                .user(user)
                .category(request.getPetsCategory())
                .brand(request.getPetsBreed())
                .birthDate(request.getBirthDate())
                .age(request.getPetsAge())
                .name(request.getPetsName())
                .gender(request.getPetsGender())
                .isPetsNeuter(request.isPetsNeuter())
                .weight(request.getPetsWeight())
                .petStatus(PetStatus.ACTIVE)
                .chip(request.getPetsChip().isEmpty() ? null : request.getPetsChip())
                .build();
    }
}