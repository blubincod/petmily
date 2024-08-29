package com.concord.petmily.domain.user.entity;

public enum Status {
    ACTIVE,    // 계정이 활성화된 상태
    INACTIVE,  // 계정이 비활성화된 상태 - 이메일 미인증
    DELETED,   // 계정이 삭제된 상태
    SUSPENDED  // 계정이 일시 정지된 상태
}
