package com.concord.petmily.domain.comment.exception;

import com.concord.petmily.common.exception.ErrorCode;
import lombok.*;

@Data
@AllArgsConstructor
public class CommentException extends RuntimeException {
    ErrorCode errorCode;
    String errorMessage;

    public CommentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}