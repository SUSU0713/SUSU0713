package com.mashibing.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.constant.IdentityConstant;
import com.mashibing.dto.TokenResult;
import com.mashibing.response.TokenResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static final String SING = "FSHSH@11!!";


    private static final String JWT_KEY_PHONE = "phone";
    private static final String JWT_KEY_IDENTITY = "identity";
    private static final String JWT_TOKEN_TYPE = "tokenType";
    private static final String JWT_TOKEN_TIME = "tokenTime";

    // 生成token
    public static String generatorToken(String passengerPhone, String identity , String tokenType){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        map.forEach(
                (k, v) -> {
                    builder.withClaim(k,v);
                }
        );
        // 整合过期时间
//         builder.withExpiresAt(date);
        String sign = builder.sign(Algorithm.HMAC256(SING));
        return sign;
    }

    // 解析token，获取手机号
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        Map<String, Claim> map = verify.getClaims();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(map.get(JWT_KEY_PHONE).asString());
        tokenResult.setIdentity(map.get(JWT_KEY_IDENTITY).asString());
        return tokenResult;
    }

    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try{
            tokenResult = parseToken(token);
        }catch (Exception e){
            System.out.println("token解析失败");
        }
        return tokenResult;
    }

//    public static void main(String[] args) {
////        String token  = generatorToken("123", "pp");
////        TokenResult tokenResult = parseToken(token);
////        System.out.println(tokenResult.getPhone() + " ---- " + tokenResult.getIdentity());
//    }
}
