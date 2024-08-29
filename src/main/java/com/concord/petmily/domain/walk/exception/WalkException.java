package com.concord.petmily.domain.walk.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class WalkException extends BaseException {
    public WalkException(ErrorCode errorCode) {
        super(errorCode); // BaseException 생성자 실행
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
