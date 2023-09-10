package com.justshop.core.common.exception;

import com.justshop.core.common.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
