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
    ACCESS_TOKEN_EXPIRED("액세스 토큰이 만료되었습니다. 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받으세요"),
    REFRESH_TOKEN_EXPIRED("리프레시 토큰이 만료되었습니다. 다시 로그인 해주세요."),
    UNAUTHORIZED_ACCESS("권한이 없는 접근입니다."),

    // User
    USER_NOT_FOUND("존재하지 않는 사용자입니다."),
    MAX_PET_PER_USER("최대 등록 가능한 반려동물 수는 10마리입니다."),
    USER_ACCOUNT_UNMATCHED("사용자와 계좌의 소유자가 다릅니다."),
    USERNAME_ALREADY_EXISTS("이미 존재하는 아이디 입니다."),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일 입니다."),

    // Pet
    PET_NOT_FOUND("존재하지 않는 반려동물입니다."),
    PET_ALREADY_REGISTERED("이 반려동물은 이미 등록되어 있습니다."),

    // Walk
    WALK_NOT_FOUND("존재하지 않는 산책 정보입니다."),

    // Other
    DATABASE_CONNECTION_FAILED("데이터베이스 연결에 실패했습니다."),
    NETWORK_ERROR("네트워크 오류가 발생했습니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),
    INTERNAL_SERVER_ERROR("서버 에러가 발생했습니다.");

    private final String message;
}
