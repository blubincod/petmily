package com.concord.petmily.domain.walk.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.exception.PetException;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.entity.*;
import com.concord.petmily.domain.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.domain.walk.exception.WalkException;
import com.concord.petmily.domain.walk.exception.WalkNotFoundException;
import com.concord.petmily.domain.walk.repository.WalkActivityRepository;
import com.concord.petmily.domain.walk.repository.WalkRepository;
import com.concord.petmily.domain.walk.repository.WalkingPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 산책 관련 서비스
 * <p>
 * - 산책 위치 기록
 * - 산책 전체 정보 조회
 * - 산책 상세 정보 조회
 * - 산책 목표 설정
 * - 산책 목표 조회
 * - 산책 목표 선택
 */
@Service
@RequiredArgsConstructor
public class WalkServiceImpl implements WalkService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final WalkRepository walkRepository;
    private final WalkingPetRepository walkingPetRepository;
    private final WalkActivityRepository walkActivityRepository;

    // 회원 정보 조회
    private User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return user;
    }

    // 회원 산책 정보 조회
    private Walk getWalk(Long walkId) {
        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));
        return walk;
    }

    /**
     * 산책 시작 및 산책 정보 기록
     */
    @Override
    @Transactional
    public WalkDto startWalk(String username, List<Long> petIds, LocalDateTime startTime) {
        User user = getUser(username);

        // 회원 산책 중 여부 확인
        if (user.isWalking) {
            throw new WalkException(ErrorCode.WALK_ALREADY_IN_PROGRESS);
        }

        // TODO 리팩토링 : 깔끔하게 정리
        Walk walk = new Walk();
        walk.setUser(user);
        walk.setStartTime(startTime);
        walk.setWalkStatus(WalkStatus.IN_PROGRESS);
        walkRepository.save(walk);

        List<WalkingPet> walkingPets = new ArrayList<>();

        for (int i = 0; i < petIds.size(); i++) {
            Pet pet = petRepository.findById(petIds.get(i))
                    .orElseThrow(() -> new PetException(ErrorCode.PET_NOT_FOUND));

            // 주인 확인
            if (pet.getUserId() != user.getId()) {
                throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
            }

            // TODO null 금지
            WalkingPet walkingPet = new WalkingPet();
            walkingPet.setWalk(walk);
            walkingPet.setPet(pet);

            WalkingPetId walkingPetId = new WalkingPetId();
            walkingPetId.setWalkId(walk.getId());
            walkingPetId.setPetId(pet.getId());
            walkingPet.setId(walkingPetId);

            walkingPets.add(walkingPet);
        }
        walkingPetRepository.saveAll(walkingPets);

        updateWalkingStatus(user, true);

        // Walk Entity를 WalkDto로 변환
        return WalkDto.fromEntity(walk);
    }

    /**
     * 산책 종료 및 산책 정보 기록
     */
    @Override
    @Transactional
    public WalkDto endWalk(Long walkId, String username, WalkDto walkDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }

        // 종료된 산책 여부 확인
        if (walk.getWalkStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }

        // 총 시간 계산
        double calculatedDuration = calculateDuration(walk.getStartTime(), walkDto.getEndTime());

        walk.setEndTime(walkDto.getEndTime());
        walk.setDistance(walkDto.getDistance());
        walk.setDuration(calculatedDuration);
        walk.setWalkStatus(WalkStatus.TERMINATED);
        walkRepository.save(walk);

        updateWalkingStatus(user, false);

        // Walk Entity를 WalkDto로 변환
        return WalkDto.fromEntity(walk);
    }

    // 산책 상태 갱신 메서드
    private void updateWalkingStatus(User user, boolean isWalking) {
        user.setIsWalking(isWalking);
        userRepository.save(user);
    }

    // 산책 시간 계산 메서드
    private double calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * 산책 활동 기록
     */
    @Override
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        WalkingPet walkingPet = walkingPetRepository.findByWalkIdAndPetId(walkId, walkActivityDto.getPetId())
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.PET_NOT_IN_THIS_WALK));

        Pet pet = walkingPet.getPet();

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }
        // 종료된 산책 여부 확인
        if (walk.getWalkStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }
        // 회원의 반려동물인지 확인
        validatePetOwnership(user, pet);

        WalkActivity walkActivity = new WalkActivity();
        walkActivity.setWalkingPet(walkingPet);
        walkActivity.setActivityType(walkActivityDto.getActivity());
        walkActivity.setLatitude(walkActivityDto.getLatitude());
        walkActivity.setLongitude(walkActivityDto.getLongitude());
        walkActivity.setTimestamp(walkActivityDto.getTimestamp());
        walkActivityRepository.save(walkActivity);

        return walkActivityDto;
    }

    // 회원의 반려동물인지 유효성 검사
    private void validatePetOwnership(User user, Pet pet) {
        if (pet.getUserId() != user.getId()) {
            throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
        }
    }

    /**
     * 회원의 모든 반려동물의 산책 기록 조회
     */
    @Override
    public List<WalkDto> getUserPetsWalks(Long userId, LocalDate startDate, LocalDate endDate) {

//        User user = getUser(username);
//
//        List<Walk> walkList = walkRepository.findByUserId(user.getId());
//
//        // Walk 엔티티 리스트를 WalkDto 리스트로 변환
//        List<WalkDto> walkDtoList = walkList.stream()
//                .map(WalkDto::fromEntity) // 각 Walk 엔티티를 WalkDto로 변환
//                .collect(Collectors.toList());

        return null;
    }

    /**
     * 반려동물의 전체 산책 기록 조회
     */
//    @Override
//    public List<WalkDto> getPetWalks(Long petId, LocalDate startDate, LocalDate endDate) {
//        List<Walk> walks = walkRepository.findByPetIdAndDateBetween(petId, startDate, endDate);
//
//        List<WalkSummaryDto> walkSummaries = walks.stream()
//                .map(this::convertToSummary)
//                .collect(Collectors.toList());
//
//        WalkStatisticsDto statistics = calculateStatistics(walks);

//        return null;
//    }

//    private WalkSummaryDto convertToSummary(Walk walk) {
//        // Walk 엔티티를 WalkSummaryDto로 변환하는 로직
//    }
//
//    private WalkStatisticsDto calculateStatistics(List<Walk> walks) {
//        // 주어진 산책 목록에 대한 통계를 계산하는 로직
//    }


    /**
     * 반려동물의 특정 산책 정보 조회
     */
//    @Override
//    public WalkDto getPetWalk(Pet pet) {
//
//        return null;
//    }

//    /**
//     * 회원의 전체 반려동물 산책 기록 일별 조회
//     */
//    @Override
//    public Map<LocalDate, List<WalkStatisticsDto>> getUserDailyWalks(Long userId) {
//        return null;
//    }

}
