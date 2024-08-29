package com.concord.petmily.domain.user.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

public class UserException extends BaseException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
