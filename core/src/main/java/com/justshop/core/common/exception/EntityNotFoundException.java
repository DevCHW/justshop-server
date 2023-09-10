package com.justshop.core.common.exception;

import com.justshop.core.common.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
