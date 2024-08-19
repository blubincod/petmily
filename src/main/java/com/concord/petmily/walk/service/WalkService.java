package com.concord.petmily.walk.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.user.entity.User;
import com.concord.petmily.user.exception.UserNotFoundException;
import com.concord.petmily.user.repository.UserRepository;
import com.concord.petmily.walk.dto.WalkActivityDto;
import com.concord.petmily.walk.dto.WalkDto;
import com.concord.petmily.walk.entity.Walk;
import com.concord.petmily.walk.entity.WalkActivity;
import com.concord.petmily.walk.entity.WalkGoal;
import com.concord.petmily.walk.entity.WalkStatus;
import com.concord.petmily.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.walk.exception.WalkException;
import com.concord.petmily.walk.exception.WalkNotFoundException;
import com.concord.petmily.walk.repository.WalkActivityRepository;
import com.concord.petmily.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

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
public class WalkService {

    private final UserRepository userRepository;
    private final WalkRepository walkRepository;
    private final WalkActivityRepository walkActivityRepository;

    /**
     * 산책 시작
     */
    public WalkDto startWalk(String username, WalkDto walkDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 회원 산책 중 여부 확인
        if (user.isWalking) {
            throw new WalkException(ErrorCode.WALK_ALREADY_IN_PROGRESS);
        }

        // TODO 반려동물 산책 그룹

        // TODO 리팩토링
        Walk walk = new Walk();
        walk.setUser(user);
        walk.setDistance(0.0);
        walk.setDuration(0.0);
        walk.setStartTime(walkDto.getStartTime());
        walk.setStatus(WalkStatus.IN_PROGRESS);
        walkRepository.save(walk);

        updateWalkingStatus(user, true);

        // Walk Entity를 WalkDto로 변환
        return WalkDto.fromEntity(walk);
    }

    /**
     * 산책 종료
     */
    public WalkDto endWalk(Long walkId, String username, WalkDto walkDto) {

        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }

        // 종료된 산책 여부 확인
        if (walk.getStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }

//        if (!user.isWalking) {
//            throw new WalkException(ErrorCode.WALK_ALREADY_NOT_IN_PROGRESS);
//        }

        // TODO 총 거리, 총 시간, 끝난 시간
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
     * 산책 활동 기록 메서드
     */
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto) {

        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

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
        walkActivity.setLatitude(walkActivityDto.getLatitude());
        walkActivity.setLongitude(walkActivityDto.getLongitude());
        walkActivity.setTimestamp(walkActivityDto.getTimestamp());
        walkActivityRepository.save(walkActivity);

        return walkActivityDto;
    }

    // 산책 정보 전체 조회
    public void getWalks() {

    }

    // 산책 정보 조회
    public void getWalk() {

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
