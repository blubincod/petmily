package com.concord.petmily.domain.walk.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkActivity;
import com.concord.petmily.domain.walk.entity.WalkGoal;
import com.concord.petmily.domain.walk.entity.WalkStatus;
import com.concord.petmily.domain.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.domain.walk.exception.WalkException;
import com.concord.petmily.domain.walk.exception.WalkNotFoundException;
import com.concord.petmily.domain.walk.repository.WalkActivityRepository;
import com.concord.petmily.domain.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final WalkRepository walkRepository;
    private final WalkActivityRepository walkActivityRepository;

    // User 엔티티를 조회
    private User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return user;
    }

    // User 산책 엔티티를 조회
    private Walk getWalk(Long walkId) {
        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));
        return walk;
    }

    /**
     * 산책 시작 및 산책 정보 기록
     */
    public WalkDto startWalk(String username, WalkDto walkDto) {
        User user = getUser(username);

        // 회원 산책 중 여부 확인
        if (user.isWalking) {
            throw new WalkException(ErrorCode.WALK_ALREADY_IN_PROGRESS);
        }

        // TODO 반려동물 산책 그룹

        // TODO 리팩토링
        Walk walk = new Walk();
        walk.setUser(user);
        walk.setStartTime(walkDto.getStartTime());
        walk.setStatus(WalkStatus.IN_PROGRESS);
        walkRepository.save(walk);

        updateWalkingStatus(user, true);

        // Walk Entity를 WalkDto로 변환
        return WalkDto.fromEntity(walk);
    }

    /**
     * 산책 종료 및 산책 정보 기록
     */
    @Transactional
    public WalkDto endWalk(Long walkId, String username, WalkDto walkDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }

        // 종료된 산책 여부 확인
        if (walk.getStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }

        // 총 거리 및 총 시간 계산
        double calculatedDistance = calculateDistance(walk);
        double calculatedDuration = calculateDuration(walk.getStartTime(), walkDto.getEndTime());

        walk.setEndTime(walkDto.getEndTime());
        walk.setDistance(calculatedDistance);
        walk.setDuration(calculatedDuration);
        walk.setStatus(WalkStatus.TERMINATED);
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

    // 산책 거리 계산 메서드
    private double calculateDistance(Walk walk) {
        // TODO 거리 계산 로직
        return 0.0;
    }

    // 산책 시간 계산 메서드
    private double calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * 산책 활동 기록
     */
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }
        // 종료된 산책 여부 확인
        if (walk.getStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }

        WalkActivity walkActivity = new WalkActivity();
        walkActivity.setWalk(walk);
        walkActivity.setType(walkActivityDto.getActivity());
        walkActivity.setLatitude(walkActivityDto.getLatitude());
        walkActivity.setLongitude(walkActivityDto.getLongitude());
        walkActivity.setTimestamp(walkActivityDto.getTimestamp());
        walkActivityRepository.save(walkActivity);

        return walkActivityDto;
    }

    /**
     * 회원의 모든 반려동물의 산책 기록 조회
     */
    public List<WalkDto> getUserPetsWalks(String username){

        User user = getUser(username);

        List<Walk> walkList = walkRepository.findByUserId(user.getId());

        // Walk 엔티티 리스트를 WalkDto 리스트로 변환
        List<WalkDto> walkDtoList = walkList.stream()
                .map(WalkDto::fromEntity) // 각 Walk 엔티티를 WalkDto로 변환
                .collect(Collectors.toList());

        return walkDtoList;
    }

    /**
     * 반려동물의 전체 산책 기록 조회
     */
    public void getPetWalks(Long petId) {

        List<Walk> walks = walkRepository.findByUserId(petId);
    }

    /**
     * 반려동물의 특정 산책 정보 조회
     */
    public void getPetWalk(Pet pet) {

    }

    /**
     * 산책 목표 설정
     * 일주일 기준으로 목표 설정
     * - 산책 횟수 (1일 1회 기준)
     * - 산책 시 시간 (분 기준)
     */
    public void setWeeklyWalkGoal() {

    }

    /**
     * 산책 목표 조회
     */
    public WalkGoal getWalkGoal() {
        // 설정된 산책 목표를 조회하여 반환

        return null;
    }

    /**
     * 산책 목표 수정
     */
    public void updateWalkGoal(int newWalkCount, int newWalkDurationMinutes) {
        // 기존 산책 목표를 새로운 값으로 수정
    }

    /**
     * 산책 목표 삭제
     */
    public void deleteWalkGoal() {
        // 설정된 산책 목표를 삭제
    }

}
