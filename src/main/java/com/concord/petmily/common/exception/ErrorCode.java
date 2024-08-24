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
    PET_NOT_FOUND("등록되지 않은 반려동물입니다."),
    PET_ALREADY_REGISTERED("해당 반려동물은 이미 등록되어 있습니다."),
    PET_OWNER_MISMATCH("해당 반려동물의 주인이 아닙니다."),

    // Walk
    WALK_NOT_FOUND("존재하지 않는 산책 정보입니다."),
    WALK_ACCESS_DENIED("해당 산책에 대한 접근 권한이 없습니다."),
    WALK_ALREADY_IN_PROGRESS("현재 진행 중인 산책이 있습니다."),
    WALK_ALREADY_TERMINATED("이미 종료된 산책입니다."),
    PET_NOT_IN_THIS_WALK("해당 반려동물이 현재 산책에 참여하고 있지 않습니다."),

    // Post
    POST_NOT_FOUND("존재하지 않는 게시물입니다."),
    POST_CATEGORY_NOT_FOUND("존재하지 않는 게시물 카테고리입니다."),
    POST_ALREADY_DELETED("이미 삭제된 게시물입니다."),
    HASHTAG_NOT_FOUND("해시태그가 존재하지 않습니다."),
    FILE_SAVE_FAILED("이미지 저장에 실패했습니다."),
    USER_POST_UNMATCHED("사용자와 게시물 작성자가 다릅니다."),

    // Comment
    COMMENT_NOT_FOUND("존재하지 않는 댓글입니다."),
    COMMENT_ALREADY_DELETED("이미 삭제된 댓글입니다."),
    USER_COMMENT_UNMATCHED("사용자와 댓글 작성자가 다릅니다."),

    // Likes
    LIKES_ALREADY_EXISTS("이미 좋아요 되어있는 게시물입니다."),
    LIKES_NOT_FOUND("좋아요 되어있지 않은 게시물입니다."),


    // Other
    DATABASE_CONNECTION_FAILED("데이터베이스 연결에 실패했습니다."),
    NETWORK_ERROR("네트워크 오류가 발생했습니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),
    INTERNAL_SERVER_ERROR("서버 에러가 발생했습니다.");

    private final String message;
}
