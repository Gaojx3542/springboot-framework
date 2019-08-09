package com.newlandpay.newretail.appstore.exception;


import com.newlandpay.newretail.appstore.common.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BizException.class, AuthException.class})
    public CommonResp bizExceptionHandler(HttpServletRequest request, final Exception e,
                                          HttpServletResponse response) {


        CommonResp.CommonRespBuilder builder = CommonResp.builder();

        String reqUrl = request != null ? request.getRequestURI() : "未知";
        if (e instanceof BizException) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            BizException exception = (BizException) e;
            builder.code(exception.getCode())
                    .msg(exception.getMessage()==null ? "" : exception.getMessage());
            log.error("请求[" + reqUrl + "], 异常[" + exception.getMessage() + "]", exception);
        }else if(e instanceof AuthException){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            AuthException exception = (AuthException)e;
            builder.code(exception.getCode())
                    .msg(exception.getMessage()==null ? "" : exception.getMessage());
            log.error("请求[" + reqUrl + "], 异常[" + exception.getMessage() + "]", exception);
        }

        return builder.build();
    }



    @ExceptionHandler(value = {RuntimeException.class})
    public CommonResp runtimeExceptionHandler(HttpServletRequest request, final Exception e,
                                           HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        CommonResp.CommonRespBuilder builder = CommonResp.builder();

        String reqUrl = request != null ? request.getRequestURI() : "未知";
        if (e instanceof RuntimeException) {
            RuntimeException exception = (RuntimeException) e;
            builder.code(99)
                    .msg(exception.getMessage()==null ? exception.toString() : exception.getMessage());
            log.error("请求[" + reqUrl + "], 异常[" + exception.getMessage() + "]", exception);
        }

        return builder.build();
    }
}
