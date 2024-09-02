package com.mashibing.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String SING = "FSHSH@11!!";

    // 生成token
    public static String generatorToken(Map<String, String> map ){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();

        map.forEach(
                (k, v) -> {
                    builder.withClaim(k,v);
                }
        );

        String sign = builder.sign(Algorithm.HMAC256(SING));
        return sign;
    }

    // 解析token，获取手机号
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify.getClaims();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("pass", "123");
        String token = generatorToken(map);
        System.out.println(token);
        Map<String, Claim> result = parseToken(token);
        System.out.println(result.get("pass").asString());
    }
}
