package com.concord.petmily.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 코드에 대한 에러 메시지를 정의하는 Enum 클래스
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Auth
    INVALID_CREDENTIALS("잘못된 사용자 이름 또는 비밀번호입니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다. 다시 로그인 해주세요."),
    UNAUTHORIZED_ACCESS("권한이 없는 접근입니다."),

    // User
    USER_NOT_FOUND("존재하지 않는 사용자입니다."),
    EMAIL_ALREADY_REGISTERED("이 이메일 주소는 이미 등록되어 있습니다."),
    MAX_PET_PER_USER("최대 등록 가능한 반려동물 수는 10마리입니다."),
    USER_ACCOUNT_UNMATCH("사용자와 계좌의 소유자가 다릅니다.");

    // Pet

    private final String message;
}
