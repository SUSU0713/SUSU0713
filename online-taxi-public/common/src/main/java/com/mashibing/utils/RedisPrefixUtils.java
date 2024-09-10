package com.mashibing.utils;

public class RedisPrefixUtils {

    // redis中存储验证码的key
    private static final String verificationCodePrefix = "passenger-verification-code-";
    // redis中存储token的key
    private static final String TOKEN_PREFIX = "token-";


    public static String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    public static String generatorKeyByToken(String passengerPhone, String identity, String tokenType){
        return TOKEN_PREFIX + passengerPhone + "-" + identity + "-" + tokenType;
    }
}
