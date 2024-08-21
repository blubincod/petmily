package com.concord.petmily.domain.walk.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalkException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public WalkException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}