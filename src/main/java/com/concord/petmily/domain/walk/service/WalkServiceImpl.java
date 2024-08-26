package com.concord.petmily.domain.walk.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.pet.entity.Pet;
import com.concord.petmily.domain.pet.exception.PetException;
import com.concord.petmily.domain.pet.repository.PetRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import com.concord.petmily.domain.walk.dto.*;
import com.concord.petmily.domain.walk.entity.*;
import com.concord.petmily.domain.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.domain.walk.exception.WalkException;
import com.concord.petmily.domain.walk.exception.WalkNotFoundException;
import com.concord.petmily.domain.walk.repository.WalkActivityRepository;
import com.concord.petmily.domain.walk.repository.WalkParticipantRepository;
import com.concord.petmily.domain.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final PetRepository petRepository;
    private final WalkRepository walkRepository;
    private final WalkParticipantRepository walkParticipantRepository;
    private final WalkActivityRepository walkActivityRepository;

    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 회원 산책 정보 조회
    private Walk getWalk(Long walkId) {
        return walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));
    }

    /**
     * 산책 시작 및 산책 정보 기록
     */
    @Override
    @Transactional
    public WalkDto startWalk(String username, List<Long> petIds, LocalDateTime startTime) {
        User user = getUser(username);

        // 회원 산책 중 여부 확인
        if (user.isWalking) {
            throw new WalkException(ErrorCode.WALK_ALREADY_IN_PROGRESS);
        }

        // TODO 리팩토링 : 깔끔하게 정리
        Walk walk = new Walk();
        walk.setUser(user);
        walk.setStartTime(startTime);
        walk.setWalkStatus(WalkStatus.IN_PROGRESS);
        walkRepository.save(walk);

        List<WalkParticipant> walkParticipants = new ArrayList<>();

        for (int i = 0; i < petIds.size(); i++) {
            Pet pet = petRepository.findById(petIds.get(i))
                    .orElseThrow(() -> new PetException(ErrorCode.PET_NOT_FOUND));

            // 주인 확인
            if (pet.getUser().getId() != user.getId()) {
                throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
            }

            // TODO null 금지
            WalkParticipant walkParticipant = new WalkParticipant();
            walkParticipant.setWalk(walk);
            walkParticipant.setPet(pet);

            WalkParticipantId walkParticipantId = new WalkParticipantId();
            walkParticipantId.setWalkId(walk.getId());
            walkParticipantId.setPetId(pet.getId());
            walkParticipant.setId(walkParticipantId);

            walkParticipants.add(walkParticipant);
        }
        walkParticipantRepository.saveAll(walkParticipants);

        updateWalkingStatus(user, true);

        // Walk Entity를 WalkDto로 변환
        return WalkDto.fromEntity(walk);
    }

    /**
     * 산책 종료 및 산책 정보 기록
     */
    @Override
    @Transactional
    public WalkDto endWalk(Long walkId, String username, WalkDto walkDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }

        // 종료된 산책 여부 확인
        if (walk.getWalkStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }

        // 총 시간 계산
        long calculatedDuration = calculateDuration(walk.getStartTime(), walkDto.getEndTime());

        walk.setEndTime(walkDto.getEndTime());
        walk.setDistance(walkDto.getDistance());
        walk.setDuration(calculatedDuration);
        walk.setWalkStatus(WalkStatus.TERMINATED);
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

    // 산책 시간 계산 메서드
    private long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * 산책 활동 기록
     */
    @Override
    public WalkActivityDto logWalkActivity(Long walkId, String username, WalkActivityDto walkActivityDto) {

        Walk walk = getWalk(walkId);

        User user = getUser(username);

        WalkParticipant walkParticipant = walkParticipantRepository.findByWalkIdAndPetId(walkId, walkActivityDto.getPetId())
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.PET_NOT_IN_THIS_WALK));

        Pet pet = walkParticipant.getPet();

        // 산책 소유자 확인
        if (!walk.getUser().getUsername().equals(username)) {
            throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        }
        // 종료된 산책 여부 확인
        if (walk.getWalkStatus() == WalkStatus.TERMINATED) {
            throw new WalkException(ErrorCode.WALK_ALREADY_TERMINATED);
        }
        // 회원의 반려동물인지 확인
        validatePetOwnership(user, pet);

        WalkActivity walkActivity = new WalkActivity();
        walkActivity.setWalkParticipant(walkParticipant);
        walkActivity.setActivityType(walkActivityDto.getActivity());
        walkActivity.setLatitude(walkActivityDto.getLatitude());
        walkActivity.setLongitude(walkActivityDto.getLongitude());
        walkActivity.setTimestamp(walkActivityDto.getTimestamp());
        walkActivityRepository.save(walkActivity);

        return walkActivityDto;
    }

    // 회원의 반려동물인지 유효성 검사
    private void validatePetOwnership(User user, Pet pet) {
        if (pet.getUser().getId() != user.getId()) {
            throw new PetException(ErrorCode.PET_OWNER_MISMATCH);
        }
    }

    /**
     * 특정 산책 기록 상세 조회
     */
    @Override
    public WalkDetailDto getWalkDetail(Long walkId) {
        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException(ErrorCode.WALK_NOT_FOUND));

        // 산책에 참여한 반려동물 아이디 리스트
        List<Long> petIds = walkParticipantRepository.findByWalkId(walk.getId())
                .stream()
                .map(walkingPet -> walkingPet.getPet().getId())
                .collect(Collectors.toList());

        // 해당 산책에 활동 리스트
        List<WalkActivity> activities = walkActivityRepository.findByWalkId(walk.getId());
        List<WalkActivityDto> activityDtos = activities.stream()
                .map(WalkActivityDto::fromEntity)
                .collect(Collectors.toList());

        return WalkDetailDto.fromEntity(walk, petIds, activityDtos);
    }

    /**
     * 반려동물 기간별 일일 산책 목록 조회
     */
    @Override
    public Page<DailyWalksDto> getPetDailyWalks(
            Long petId, String username, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        User user = getUser(username);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(ErrorCode.PET_NOT_FOUND));

        // TODO 공개/비공개 설정 추가(Pet 엔티티 isPublic 으로 구분 )
        //if (!pet.getUser().equals(user) && !pet.isPublic()) {
        //    throw new WalkAccessDeniedException(ErrorCode.WALK_ACCESS_DENIED);
        //}

        // endDate: 주어진 종료 날짜
        // plusDays(1): 종료 날짜에 하루를 더함
        // atStartOfDay(): 해당 날짜의 시작 시간(00:00:00)을 나타내는 LocalDateTime 객체
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime endDateTime = endDate != null ? endDate.plusDays(1).atStartOfDay() : LocalDateTime.MAX;

        Page<Walk> walks = walkRepository.findByWalkParticipantsPetIdAndStartTimeBetween(petId, startDateTime, endDateTime, pageable);

        Map<LocalDate, List<WalkDto>> walksByDate = walks.getContent().stream()
                .collect(Collectors.groupingBy(
                        walk -> walk.getStartTime().toLocalDate(),
                        Collectors.mapping(WalkDto::fromEntity, Collectors.toList())
                ));

        // 날짜별로 그룹화된 산책 정보를 DailyWalksDto 객체의 리스트로 변환
        List<DailyWalksDto> dailyWalks = walksByDate.entrySet().stream()
                .map(entry -> new DailyWalksDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new PageImpl<>(dailyWalks, pageable, walks.getTotalElements());
    }

    /**
     * 반려동물의 기간별 일일 산책 통계 조회
     */
    @Override
    public Page<WalkStatisticsDto> getPetDailyWalksStatistics(Long petId, String username, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        User user = getUser(username);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(ErrorCode.PET_NOT_FOUND));

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : LocalDateTime.MIN;
        LocalDateTime endDateTime = endDate != null ? endDate.plusDays(1).atStartOfDay() : LocalDateTime.MAX;

        Page<Walk> walksPage = walkRepository.findByWalkParticipantsPetIdAndStartTimeBetween(petId, startDateTime, endDateTime, pageable);

        return walksPage.map(walk -> {
            LocalDate walkDate = walk.getStartTime().toLocalDate();
            List<Walk> dailyWalks = walkRepository.findByWalkParticipantsPetIdAndStartTimeBetween(
                    petId,
                    walkDate.atStartOfDay(),
                    walkDate.plusDays(1).atStartOfDay(),
                    Pageable.unpaged()
            ).getContent();
            return createWalkStatisticsDto(petId, walkDate, dailyWalks);
        });
    }

    /**
     * 회원의 모든 반려동물의 특정 기간 산책 기록 상세 조회
     */
    @Override
    public Page<PetsWalkDetailDto> getUserAllPetsWalksDetail(String username, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        User user = getUser(username);
        Page<Walk> walksPage;

        // FIXME LocalDate -> LocalDateTime로 변경
        if (startDate != null && endDate != null) {
            walksPage = walkRepository.findByUserIdAndWalkDateBetween(user.getId(), startDate, endDate, pageable);
        } else {
            walksPage = walkRepository.findByUserId(user.getId(), pageable);
        }

        return walksPage.map(this::convertToPetsWalkDetailDto);
    }

    // Walk 엔티티를 WalkWithPetsDto로 변환
    private PetsWalkDetailDto convertToPetsWalkDetailDto(Walk walk) {
        List<Long> petIds = walkParticipantRepository.findByWalkId(walk.getId())
                .stream()
                .map(walkingPet -> walkingPet.getPet().getId())
                .collect(Collectors.toList());

        return PetsWalkDetailDto.fromEntity(walk, petIds);
    }

    /**
     * 회원의 모든 반려동물별 전체 산책 통계 조회
     */
    @Override
    public Page<WalkStatisticsDto> getUserPetsWalkStatistics(String username, Pageable pageable) {
        User user = getUser(username);

        Page<Pet> petsPage = petRepository.findByUserId(user.getId(), pageable);

        return petsPage.map(pet -> {
            // 참여한 산책을 찾기
            List<WalkParticipant> walkParticipants = walkParticipantRepository.findByIdPetId(pet.getId());
            // 참여한 산책을 리스트로 변환
            List<Walk> walks = walkParticipants.stream()
                    .map(WalkParticipant::getWalk)
                    .collect(Collectors.toList());
            return createWalkStatisticsDto(pet.getId(), null, walks);
        });
    }

    // 산책 통계 메서드
    private WalkStatisticsDto createWalkStatisticsDto(Long petId, LocalDate date, List<Walk> walks) {
        int totalWalks = walks.size();
        double totalDistance = walks.stream().mapToDouble(Walk::getDistance).sum();
        long totalDuration = walks.stream()
                .mapToLong(walk -> Duration.between(walk.getStartTime(), walk.getEndTime()).toMinutes())
                .sum();

        double avgDistance = totalWalks > 0 ? totalDistance / totalWalks : 0;
        long avgDuration = totalWalks > 0 ? totalDuration / totalWalks : 0;

        return WalkStatisticsDto.builder()
                .petId(petId)
                .date(date)
                .totalWalks(totalWalks)
                .totalDistanceKm(totalDistance)
                .totalDurationMinutes(totalDuration)
                .averageDistanceKm(avgDistance)
                .averageDurationMinutes(avgDuration)
                .build();
    }


    /**
     * 모든 반려동물에 대한 종합적인 산책 통계 조회
     */
//    @Override
//    public WalkStatisticsDto getUserPetsOverallWalkStatistics(String username) {
//        return null;
//    }

}
