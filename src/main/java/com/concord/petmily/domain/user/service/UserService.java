package com.concord.petmily.domain.user.service;

import com.concord.petmily.domain.user.dto.AddAdminRequest;
import com.concord.petmily.domain.user.dto.AddUserRequest;
import com.concord.petmily.domain.user.entity.User;

public interface UserService {
    // username으로 회원조회
    User findByUsername(String username);
    // userId로 회원조회
    User findById(Long userId);
    // 회원가입
    Long registerUser(AddUserRequest dto);
    // 회원 정보 수정
    User updateUser(String username, Long userId, AddUserRequest dto);
    // 회원 Role, Status 변경 (관리자 전용)
    User updateUserByAdmin(String username, Long userId, AddAdminRequest dto);
    // 회원 삭제 기능
    void deleteUser(String username, Long userId);
    // 회원 통계 조회 (관리자 전용)
    String getStatistics(String username);
}
