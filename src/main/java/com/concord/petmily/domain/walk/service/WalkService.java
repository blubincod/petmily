package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.dto.WalkActivityDto;
import com.concord.petmily.domain.walk.dto.WalkDto;
import com.concord.petmily.domain.walk.dto.WalkWithPetsDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WalkService {

    /**
     * 산책 시작/종료 및 산책 정보 저장
     */
    WalkDto startWalk(String username, List<Long> petList, LocalDateTime startTime);

    WalkDto endWalk(Long walkId, String username, WalkDto walkDto);

    /**
     * 산책 활동 기록
     */
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto);

    /**
     * 산책 기록 조회
     */
    // 회원의 모든 반려동물의 산책 기록 조회
    public List<WalkWithPetsDto> getUserPetsWalks(Long userId, LocalDate startDate, LocalDate endDate);

//    //  반려동물의 전체 산책 정보를 조회
//    public List<WalkDto> getPetWalks(Long petId);

// 반려동물의 특정 산책 정보를 조회
//    public WalkDto getPetWalk(Pet pet);

//    // 회원의 전체 반려동물 산책 기록 일별 조회
//    Map<LocalDate, List<WalkDto>> getUserDailyWalks(Long userId);
//
//    // 특정 반려동물의 산책 일별 조회
//    Map<LocalDate, List<WalkStatisticsDto>> getPetDailyWalks(Long petId);
//
//    // 특정 반려동물의 특정 산책 상세 정보 조회
//    WalkDto getPetWalkDetail(Long petId, Long walkId);
}