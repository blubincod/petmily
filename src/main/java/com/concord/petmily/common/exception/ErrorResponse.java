package com.concord.petmily.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 클라이언트에게 전달한 에러의 응답 형태를 정의하는 클래스
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private ErrorCode errorCode;
    private String ErrorMessage;
}
