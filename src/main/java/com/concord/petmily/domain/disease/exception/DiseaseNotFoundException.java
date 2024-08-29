package com.concord.petmily.domain.disease.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class DiseaseNotFoundException extends BaseException {
    public DiseaseNotFoundException(ErrorCode errorCode) {
        super(errorCode); // BaseException 생성자 실행
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}