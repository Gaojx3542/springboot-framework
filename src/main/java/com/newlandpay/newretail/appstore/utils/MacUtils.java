package com.newlandpay.newretail.appstore.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class MacUtils {

    private static final Logger logger = LoggerFactory.getLogger(MacUtils.class);

    public static String genHMac256Key(){
        return genMacKey("HmacSHA256");
    }

    public static String genMacKey(String alg){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(alg);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.encodeBase64URLSafeString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            logger.error("生成MAC密钥失败",e);
        }
        return null;
    }

    public static String mac(byte[] data, SecretKey key){
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        byte[] signature = mac.doFinal(data);
        return Base64.encodeBase64String(signature);
    }

    public static String mac(byte[] data, String keyCodedBase64) {
        byte[] keyBytes = Base64.decodeBase64(keyCodedBase64);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        return mac(data, secretKey);
    }
}
