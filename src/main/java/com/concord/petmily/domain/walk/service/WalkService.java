package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.entity.Walk;
import com.concord.petmily.domain.walk.entity.WalkGoal;

import java.time.LocalDateTime;
import java.util.List;

public interface WalkService {

    /**
     * 산책 시작 및 산책 정보 저장
     */
    WalkDto startWalk(String username, List<Long> petList, LocalDateTime startTime);

    /**
     * 산책 종료 및 산책 정보 기록
     */
    WalkDto endWalk(Long walkId, String username, WalkDto walkDto);

    /**
     * 산책 활동 기록
     */
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto);

    /**
     * 회원의 모든 반려동물의 산책 기록 조회
     */
    public List<WalkDto> getUserPetsWalks(String username);

    /**
     * 반려동물의 전체 산책 정보를 조회
     */
    public void getPetWalks(Long petId);

    /**
     * 반려동물의 특정 산책 정보를 조회
     */
    public void getPetWalk(Pet pet);

    /**
     * 산책 목표 설정
     * 일주일 기준으로 목표 설정
     * - 산책 횟수 (1일 1회 기준)
     * - 산책 시 시간 (분 기준)
     */
    public void setWeeklyWalkGoal();

    /**
     * 산책 목표 조회
     */
    public WalkGoal getWalkGoal();

    /**
     * 산책 목표 수정
     */
    public void updateWalkGoal(int newWalkCount, int newWalkDurationMinutes);

    /**
     * 산책 목표 삭제
     */
    public void deleteWalkGoal();
}
