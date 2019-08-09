package com.newlandpay.newretail.appstore.config;

import com.newlandpay.newretail.appstore.config.interceptor.RequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private RequiredInterceptor requiredInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> urls = new ArrayList<>(10);
//        urls.add("/resource/**");
/*        registry.addInterceptor(requiredInterceptor)
                .addPathPatterns("/**");
        */
        //其他拦截 ...

    }
}
