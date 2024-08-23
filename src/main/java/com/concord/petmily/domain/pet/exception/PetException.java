package com.concord.petmily.domain.pet.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class PetException extends BaseException {
    public PetException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
