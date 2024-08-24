package com.concord.petmily.domain.pet.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class PetNotFoundException extends BaseException {
    public PetNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
