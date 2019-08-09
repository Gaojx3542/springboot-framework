package com.newlandpay.newretail.appstore.exception;

import com.newlandpay.newretail.appstore.common.ApiError;

public class BizException extends RuntimeException {

    private Integer code;

    public BizException(ApiError apiError){
        super(apiError.getMsg());
        this.code = apiError.getCode();
    }

    public BizException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
