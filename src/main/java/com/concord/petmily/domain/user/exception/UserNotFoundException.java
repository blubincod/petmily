package com.concord.petmily.domain.user.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public UserNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}
