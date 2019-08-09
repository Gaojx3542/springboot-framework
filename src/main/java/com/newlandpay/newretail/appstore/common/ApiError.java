package com.newlandpay.newretail.appstore.common;

public enum ApiError {
    SYS_ERR(9999, "系统错误"),

    AUTH_ERR_REQUEST(4000, "Request类型不对"),
    AUTH_MISS_AK(4001, "请求头缺少AccessKey"),
    AUTH_MISS_AUTH(4002, "请求头缺少Authorization"),
    AUTH_MISS_NONCE(4007, "请求头缺少nonce"),
    AUTH_MISS_TIMESTAMP(4008, "请求头缺少timestamp"),
    AUTH_ERR_TIMESTAMP(4009, "无效的timestamp"),
    AUTH_ERR_REPLAY_ATTACKS(4010, "存在重放攻击风险"),
    AUTH_RAM_INVALID_AK(4003, "无效的AccessKey"),
    AUTH_RAM_INVALID_SK(4004, "找不到对应的SecurityKey"),
    AUTH_SIGN_ERROR(4005, "签名信息错误"),
    AUTH_MISS_CONTENTTYPE(4006, "缺少ContentType"),
    AUTH_NOT_FOUND(4011, "找不到授权对象"),

    ERR_CMD_EXPIRETIME(5100,"失效时间设置错误"),
    ERR_CMD_MISS_CMD_TYPE(5101, "缺失指令类型"),
    ERR_CMD_MISS_TARGET_CMD(5102, "缺失目标指令"),
    ERR_CMD_MISS_PACKAGE(5103, "缺失应用包名"),
    ERR_CMD_NOT_FOUND(5104, "未找到指令"),

    MISS_PARAM(1000, "缺失参数"),
    MISS_BEGIN_TIME(1001, "缺失开始时间"),
    MISS_END_TIME(1002, "缺失结束时间"),
    ERR_TIME_ORDER(1003, "时间顺序错误");

    private int code;
    private String msg;

    ApiError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public static ApiError getError(int code){
        for (ApiError error : ApiError.class.getEnumConstants()) {
            if(error.code == code){
                return error;
            }
        }
        return null;
    }

}
