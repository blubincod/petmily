package com.concord.petmily.common.exception;

import com.concord.petmily.auth.exception.AuthException;
import com.concord.petmily.user.exception.UserException;
import com.concord.petmily.user.exception.UserNotFoundException;
import com.concord.petmily.walk.entity.Walk;
import com.concord.petmily.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.walk.exception.WalkException;
import com.concord.petmily.walk.exception.WalkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 전역에서 발생하는 예외를 처리하는 핸들러 클래스
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** AuthException 처리
     *
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ErrorResponse handleAuthException(AuthException e) {
        log.error("AuthException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * UserNotFoundException 처리
     * 사용자를 찾을 수 없을 때 사용
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.error("UserNotFoundException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * UserException 처리
     * 사용자와 관련된 일반적인 에러 상황에서 사용
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ErrorResponse handleUserException(UserException e) {
        log.error("UserException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * WalkNotFoundException 처리
     * 산책 정보를 찾을 수 없을 때 사용
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WalkNotFoundException.class)
    public ErrorResponse handleWalkNotFoundException(WalkNotFoundException e) {
        log.error("WalkNotFoundException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
    /**
     * WalkException 처리
     * 산책과 관련된 일반적인 에러 상황에서 사용
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WalkException.class)
    public ErrorResponse handleUserException(WalkException e) {
        log.error("WalkException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
    /**
     * WalkAccessDeniedException 처리
     * 사용자가 권한 없이 산책 정보에 접근 시 사용
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(WalkAccessDeniedException.class)
    public ErrorResponse handleWalkAccessDeniedException(WalkAccessDeniedException e) {
        log.error("WalkAccessDeniedException occurred: {}", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    /** 기타 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e, WebRequest request) {
        log.error("An unexpected error occurred: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}