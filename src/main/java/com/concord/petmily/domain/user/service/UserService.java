package com.concord.petmily.domain.user.service;

import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.walk.dto.WalkDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     *
     * @param dto
     * @return
     */
    Long registerUser(AddUserRequest dto);

    /**
     *
     */
    User findById(Long userId);

    /**
     * TODO 중복 기능?
     */
    User findByUsername(String username);

    /**
     * 회원 삭제 기능
     */
    void deleteUser(String username, Long userId);

    /**
     * 회원 정지 기능
     */
    void suspendUser(String username, Long userId);

    /**
     * 회원 정지 해제 기능
     */
    void unsuspendUser(String username, Long userId);

    /**
     * 회원 통계 조회 (관리자 전용)
     */
    String getStatistics(String username);
}
