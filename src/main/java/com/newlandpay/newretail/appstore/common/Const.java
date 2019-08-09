package com.newlandpay.newretail.appstore.common;

import java.text.MessageFormat;

public class Const {
    public static final String HEADER_AK = "x-toms-api-ak";
    public static final String HEADER_AUTH = "x-toms-api-authorization";
    public static final String HEADER_NONCE = "x-toms-api-nonce";
    public static final String HEADER_TIMESTAMP = "x-toms-api-timestamp";
    public static final long REPLAY_ATTACK_TIME = 600;
    /**
     * redis保存请求随机数的key，{0}填AK,{1}填随机数
     */
    public static final MessageFormat REDIS_KEY_API = new MessageFormat("TOMSAPI:{0}:{1}");
    public static final String REDIS_V_API = "S";

    public static final String REQUEST_ATTR_REQUESTID="y-appstore-requestid";
    public static final String REQUEST_ATTR_APIACCOUNT="y-appstore-apiaccount";
    public static final String REQUEST_ATTR_AUTH_EXCEPTION = "y-appstore-auth-exception";

    public static long PUSH_TIME_MILLION = 1500L;
}
