package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.constant.TokenConstant;
import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.TokenResult;
import com.mashibing.utils.JwtUtils;
import com.mashibing.utils.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求参数中获取token，一般在Authorization请求头中
        String tokenRequest = request.getHeader("Authorization");
        boolean result = true;
        String resultString = "";
        try{
            TokenResult tokenResult = JwtUtils.parseToken(tokenRequest);
            String identity = tokenResult.getIdentity();
            String phone = tokenResult.getPhone();

            // 用这个token组成key去redis中查询
            String accessKey = RedisPrefixUtils.generatorKeyByToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
            // 进行校验
            System.out.println(stringRedisTemplate);
            String value = stringRedisTemplate.opsForValue().get(accessKey);
            // 若不存在  ||  若存在则看对不对的上
            if(StringUtils.isBlank(value) || !value.trim().equals(tokenRequest.trim())) {
                result = false;
                resultString = "token error";
            }
        }catch (Exception e){
            resultString = "token invalid";
            result = false;
        }

        if(!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }

}
