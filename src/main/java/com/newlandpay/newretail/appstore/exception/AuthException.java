package com.newlandpay.newretail.appstore.exception;

import com.newlandpay.newretail.appstore.common.ApiError;

public class AuthException extends RuntimeException {

    private Integer code;

    public AuthException(ApiError apiError){
        super(apiError.getMsg());
        this.code = apiError.getCode();
    }

    public AuthException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public AuthException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
