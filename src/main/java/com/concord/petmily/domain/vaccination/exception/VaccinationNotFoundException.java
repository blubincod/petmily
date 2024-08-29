package com.concord.petmily.domain.vaccination.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class VaccinationNotFoundException extends BaseException {
    public VaccinationNotFoundException(ErrorCode errorCode) {
        super(errorCode); // BaseException 생성자 실행
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}
