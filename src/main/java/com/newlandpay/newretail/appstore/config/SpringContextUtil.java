package com.newlandpay.newretail.appstore.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author chenkai
 * @date 2019/7/22
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    // 传入线程中
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    // 国际化使用
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }

    /// 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

//    public static ApiAccount getApiAccount(){
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        Object apiAccount = request.getAttribute(REQUEST_ATTR_APIACCOUNT);
//        return apiAccount==null ? null : (ApiAccount)apiAccount;
//    }
}
