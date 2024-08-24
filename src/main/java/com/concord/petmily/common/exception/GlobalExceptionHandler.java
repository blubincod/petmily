package com.concord.petmily.common.exception;

import com.concord.petmily.domain.auth.exception.AuthException;
import com.concord.petmily.domain.comment.exception.CommentException;
import com.concord.petmily.domain.likes.exception.LikesException;
import com.concord.petmily.domain.pet.exception.PetException;
import com.concord.petmily.domain.pet.exception.PetNotFoundException;
import com.concord.petmily.domain.post.exception.PostException;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.walk.exception.WalkAccessDeniedException;
import com.concord.petmily.domain.walk.exception.WalkException;
import com.concord.petmily.domain.walk.exception.WalkNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 전역에서 발생하는 예외를 처리하는 핸들러 클래스
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request: 클라이언트의 요청이 잘못되었을 때
    @ExceptionHandler({
            AuthException.class, UserException.class, PetException.class,
            WalkException.class, PostException.class, CommentException.class,
            LikesException.class
    })
    public ResponseEntity<ErrorResponse> handleCustomException(BaseException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getErrorCode(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 403 Forbidden: 클라이언트가 인증되었지만 해당 리소스에 접근 권한이 없을 때
    @ExceptionHandler(WalkAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(WalkAccessDeniedException e) {
        log.error("WalkAccessDeniedException occurred: {}", e.getErrorCode(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // 404 NOT FOUND: 청한 리소스를 서버에서 찾을 수 없을 때
    @ExceptionHandler({
            UserNotFoundException.class, PetNotFoundException.class, WalkNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getErrorCode(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 500 Internal Server Error: 서버에서 예기치 못한 오류가 발생했을 때 사용
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e, WebRequest request) {
        log.error("An unexpected error occurred", e);
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}