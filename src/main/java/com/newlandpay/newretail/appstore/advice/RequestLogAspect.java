package com.newlandpay.newretail.appstore.advice;

import com.newlandpay.newretail.appstore.filter.CachedRequestWrapper;
import com.newlandpay.newretail.appstore.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.newlandpay.newretail.appstore.common.Const.REQUEST_ATTR_REQUESTID;

@Slf4j
@Aspect
@Component
@Order(value = 1)
public class RequestLogAspect {

    ThreadLocal<String> requestId  = new ThreadLocal<String>();


    //    定义切点
    @Pointcut("@annotation(operation)")
    public void reqLogPointcut(Operation operation){}

    @Before("reqLogPointcut(operation)")
    public void deBefore(JoinPoint joinPoint, Operation operation){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        CachedRequestWrapper xssRequest = null;
        requestId.set((String)request.getAttribute(REQUEST_ATTR_REQUESTID));
        String contentType = request.getHeader("Content-Type");
        StringBuilder sb = new StringBuilder(1024);

        if(request instanceof CachedRequestWrapper){
            xssRequest = (CachedRequestWrapper) request;
            if(StringUtils.isBlank(contentType) || !contentType.startsWith("multipart")){
                if(xssRequest != null){
                    StringBuilder logInfo = new StringBuilder(1280);
                    logInfo.append("\n==========(").append(operation.value()).append(")请求报文:==========")
                            .append("\nrequestId=").append(request.getAttribute(REQUEST_ATTR_REQUESTID))
                            .append("\nURI=").append(request.getRequestURI());
                    //header
                    Map header = new HashMap();
                    Collections.list(request.getHeaderNames()).stream()
                            .forEach(name -> header.put(name, request.getHeader(name)));
                    logInfo.append("\nHEADER:").append(JsonUtils.obj2Str(header));
                    logInfo.append("\nPARAMS:").append(JsonUtils.obj2Str(request.getParameterMap()));
                    logInfo.append("\nBODY:").append(new String(((CachedRequestWrapper) request).getBody()));
                    log.info(logInfo.toString());
                }
            }
        }

    }


    @AfterReturning(returning = "o", pointcut = "reqLogPointcut(operation)")
    public void afterReturn(Operation operation , Object o){
        if(o == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(1024);
        sb.append("\n==========(").append(operation.value()).append(")应答内容============")
                .append("\nrequestId=").append(requestId.get())
                .append("\n"+ JsonUtils.obj2Str(o));
        log.info(sb.toString());
        sb = null;
    }
}
