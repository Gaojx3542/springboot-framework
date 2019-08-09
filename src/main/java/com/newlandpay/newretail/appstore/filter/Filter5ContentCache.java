package com.newlandpay.newretail.appstore.filter;

import com.newlandpay.newretail.appstore.common.ApiError;
import com.newlandpay.newretail.appstore.exception.AuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 对非文件上传的请求，做缓存包装，以便AOP对request的内容做签名计算
 */
@Component
@WebFilter(filterName = "contentCacheFilter", urlPatterns = {"/*"})
public class Filter5ContentCache implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String contentType = request.getContentType();
        if(StringUtils.isBlank(contentType)){
            throw new AuthException(ApiError.AUTH_MISS_CONTENTTYPE);
        }
        //对于非multipart的请求，缓存请求的content
        if( !contentType.startsWith("multipart")){

            CachedRequestWrapper req = new CachedRequestWrapper(httpServletRequest);
            //这里调用，为了使ContentCachingRequestWrapper内部创建ContentCachingInputStream
            req.getInputStream();
//            req.setAttribute(REQUEST_ATTR_REQUESTID, UUID.randomUUID().toString());
            chain.doFilter(req, response);
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
