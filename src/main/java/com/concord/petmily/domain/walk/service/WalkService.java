package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.entity.WalkGoal;
import com.concord.petmily.domain.walk.dto.WalkActivityDto;

public interface WalkService {

    /**
     * 산책 시작
     */
    WalkDto startWalk(String username, WalkDto walkDto);

    /**
     * 산책 종료
     */
    WalkDto endWalk(Long walkId, String username, WalkDto walkDto);

    /**
     * 산책 활동 기록 메서드
     */
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto);

    /**
     * 산책 정보 전체 조회
     */
    public void getWalks();

    /**
     * 산책 정보 조회
     */
    public void getWalk();

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
