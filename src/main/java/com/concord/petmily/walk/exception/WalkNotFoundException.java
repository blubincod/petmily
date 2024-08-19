package com.concord.petmily.walk.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalkNotFoundException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public WalkNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}
