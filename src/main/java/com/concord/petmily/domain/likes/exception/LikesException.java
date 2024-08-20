package com.concord.petmily.domain.likes.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.*;

@Data
@AllArgsConstructor
public class LikesException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public LikesException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}