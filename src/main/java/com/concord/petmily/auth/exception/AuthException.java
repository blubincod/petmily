package com.concord.petmily.auth.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.*;

@Data
@AllArgsConstructor
public class AuthException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public AuthException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = getErrorMessage();
    }
}
