package com.concord.petmily.domain.walk.service;

import com.concord.petmily.domain.walk.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WalkService {

    /**
     * 산책 시작/종료 활동, 산책 정보 저장
     */
    // 산책 시작
    StartWalkDto.Response startWalk(String username, List<Long> petList, LocalDateTime startTime);

    // 산책 종료
    WalkDto endWalk(Long walkId, String username, WalkDto walkDto);

    // 산책 활동 기록
    WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto);

    /**
     * 산책 기록 조회
     */
    // 특정 산책 상세 기록 조회
    WalkDetailDto getWalkDetail(Long walkId);

    // 반려동물 기간별 일일 산책 목록 조회
    Page<DailyWalksDto> getPetDailyWalks(Long petId, String username, LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 반려동물의 기간별 일일 산책 통계 조회
    Page<WalkStatisticsDto> getPetDailyWalksStatistics(Long petId, String username, LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 회원의 모든 반려동물의 특정 기간 산책 기록 상세 조회
    Page<PetsWalkDetailDto> getUserAllPetsWalksDetail(String username, LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 회원의 모든 반려동물별 전체 산책 통계 조회
    Page<WalkStatisticsDto> getUserPetsWalkStatistics(String username, Pageable pageable);

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