package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.dto.WalkGoalDto;

public interface WalkGoalService {
    WalkGoalDto createWalkGoal(Long petId, WalkGoalDto walkGoalDto);

    WalkGoalDto getWalkGoal(Long petId);

    WalkGoalDto updateWalkGoal(Long petId, WalkGoalDto walkGoalDto);

    void deleteWalkGoal(Long petId);
}
