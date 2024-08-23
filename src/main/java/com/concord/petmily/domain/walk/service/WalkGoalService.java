package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.entity.WalkGoal;

public interface WalkGoalService {
    void setWeeklyWalkGoal();

    WalkGoal getWalkGoal();

    void updateWalkGoal(int newWalkCount, int newWalkDurationMinutes);

    void deleteWalkGoal();
}
