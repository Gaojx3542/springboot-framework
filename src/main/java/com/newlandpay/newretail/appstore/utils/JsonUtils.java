package com.newlandpay.newretail.appstore.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configOverride(java.util.Date.class)
                .setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd HH:mm:ss"));
        //为null的属性，不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }


    public static <T> String obj2Str(T obj){
        if(obj == null)
            return null;
        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("Parse obj to String error", e);
            return null;
        }
    }

    public static <T> T str2Obj(String str, Class<T> clz){
        if(StringUtils.isEmpty(str) || clz == null)
            return null;

        try {
            return clz.equals(String.class) ? (T)str : objectMapper.readValue(str, clz);
        } catch (IOException e) {
            logger.warn("Parse String to obj error", e);
            return null;
        }
    }
}
