package com.concord.petmily.post.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.*;

@Data
@AllArgsConstructor
public class PostException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public PostException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}