package com.concord.petmily.domain.auth.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;
import lombok.*;

public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
