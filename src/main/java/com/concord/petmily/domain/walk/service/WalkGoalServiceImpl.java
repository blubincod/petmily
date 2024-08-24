package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.entity.WalkGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalkGoalServiceImpl implements WalkGoalService {

    /**
     * 산책 목표 설정
     * 일주일 기준으로 목표 설정
     * - 산책 횟수 (1일 1회 기준)
     * - 산책 시 시간 (분 기준)
     */
    @Override
    public void setWeeklyWalkGoal() {

    }

    /**
     * 산책 목표 조회
     */
    @Override
    public WalkGoal getWalkGoal() {
        // 설정된 산책 목표를 조회하여 반환

        return null;
    }

    /**
     * 산책 목표 수정
     */
    @Override
    public void updateWalkGoal(int newWalkCount, int newWalkDurationMinutes) {
        // 기존 산책 목표를 새로운 값으로 수정
    }

    /**
     * 산책 목표 삭제
     */
    @Override
    public void deleteWalkGoal() {
        // 설정된 산책 목표를 삭제
    }

}
