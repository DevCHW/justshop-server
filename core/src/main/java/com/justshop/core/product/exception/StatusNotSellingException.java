package com.justshop.core.product.exception;

import com.justshop.core.common.ErrorCode;
import com.justshop.core.common.exception.BusinessException;

public class StatusNotSellingException extends BusinessException {

    public StatusNotSellingException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
