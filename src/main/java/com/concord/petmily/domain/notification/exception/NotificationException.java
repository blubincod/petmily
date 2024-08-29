package com.concord.petmily.domain.notification.exception;

import com.concord.petmily.common.exception.BaseException;
import com.concord.petmily.common.exception.ErrorCode;

public class NotificationException extends BaseException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getErrorMessage() {
        return getErrorCode().getMessage();
    }
}