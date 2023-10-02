package com.justshop.exception;

import com.justshop.error.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
