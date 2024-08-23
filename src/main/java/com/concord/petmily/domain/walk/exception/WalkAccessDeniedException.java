package com.concord.petmily.domain.walk.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class WalkAccessDeniedException extends BaseException {
    public WalkAccessDeniedException(ErrorCode errorCode) {
        super(errorCode); // BaseException 생성자 실행
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
