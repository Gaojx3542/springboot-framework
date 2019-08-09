package com.newlandpay.newretail.appstore;

import com.newlandpay.newretail.appstore.utils.MacUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class SomeTest {

    @Test
    public void genAccessKeyTest(){
        System.out.println(RandomStringUtils.randomAlphanumeric(16));
    }

    @Test
    public void getRamTest(){
        String ak = RandomStringUtils.randomAlphanumeric(16);
        String sk = MacUtils.genHMac256Key();
        System.out.printf("\nak=[%s]; sk=[%s]\n", ak, sk);
    }

    @Test
    public void testSign(){
        String s = "ak=kjuH10jQdIX8PloC&method=GET";
        String sk = "nZaaR9XWlqMJKVxFzcPIU8c9glNNKEYoRm92bmeaU14";
        System.out.println(MacUtils.mac(s.getBytes(), sk));
    }
}
