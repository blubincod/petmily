package com.concord.petmily.domain.walk.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.exception.PetException;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.walk.dto.WalkGoalDto;
import com.concord.petmily.domain.walk.entity.WalkGoal;
import com.concord.petmily.domain.walk.exception.WalkException;
import com.concord.petmily.domain.walk.repository.WalkGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalkGoalServiceImpl implements WalkGoalService {

    private final WalkGoalRepository walkGoalRepository;
    private final PetRepository petRepository;

    /**
     * 산책 목표 설정
     * 일주일 기준으로 목표 설정
     * - 산책 횟수 (1일 1회 기준)
     * - 산책 시 시간 (분 기준)
     */
    @Override
    @Transactional
    public WalkGoalDto createWalkGoal(Long petId, WalkGoalDto walkGoalDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(ErrorCode.PET_NOT_FOUND));

        if (walkGoalRepository.existsById(petId)) {
            throw new WalkException(ErrorCode.WALK_GOAL_ALREADY_EXISTS);
        }

        WalkGoal walkGoal = WalkGoal.builder()
                .pet(pet)
                .title(walkGoalDto.getTitle())
                .dailyTargetMinutes(walkGoalDto.getDailyTargetMinutes())
                .targetStartTime(walkGoalDto.getTargetStartTime())
                .build();

        WalkGoal savedGoal = walkGoalRepository.save(walkGoal);
        return WalkGoalDto.fromEntity(savedGoal);
    }

    /**
     * 산책 목표 조회
     */
    @Override
    public WalkGoalDto getWalkGoal(Long petId) {
        return walkGoalRepository.findById(petId)
                .map(WalkGoalDto::fromEntity)
                .orElse(null);
    }

    /**
     * 산책 목표 수정
     */
    @Override
    @Transactional
    public WalkGoalDto updateWalkGoal(Long petId, WalkGoalDto walkGoalDto) {
        WalkGoal walkGoal = walkGoalRepository.findById(petId)
                .orElseThrow(() -> new WalkException(ErrorCode.WALK_GOAL_NOT_FOUND));

        walkGoal.setTitle(walkGoalDto.getTitle());
        walkGoal.setDailyTargetMinutes(walkGoalDto.getDailyTargetMinutes());
        walkGoal.setTargetStartTime(walkGoalDto.getTargetStartTime());

        WalkGoal updatedGoal = walkGoalRepository.save(walkGoal);
        return WalkGoalDto.fromEntity(updatedGoal);
    }

    /**
     * 산책 목표 삭제
     */
    @Override
    @Transactional
    public void deleteWalkGoal(Long petId) {
        if (!walkGoalRepository.existsById(petId)) {
            throw new WalkException(ErrorCode.WALK_GOAL_NOT_FOUND);
        }
        walkGoalRepository.deleteById(petId);
    }
}
