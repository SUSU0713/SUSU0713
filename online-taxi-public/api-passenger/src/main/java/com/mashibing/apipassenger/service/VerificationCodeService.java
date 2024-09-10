package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.constant.IdentityConstant;
import com.mashibing.constant.TokenConstant;
import com.mashibing.dto.ResponseResult;
import com.mashibing.request.VerificationCodeDTO;
import com.mashibing.response.TokenResponse;
import com.mashibing.utils.JwtUtils;
import com.mashibing.utils.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;




    public ResponseResult generateCode(String passengerPhone){
        // 生成验证码
        int code = serviceVerificationCodeClient.getNumberCode(6).getData().getNumberCode();
        System.out.println("生成验证码："+code);
        // 存入redis
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        stringRedisTemplate.opsForValue().set(key, code+"", 2, TimeUnit.MINUTES);
        System.out.println("存入redis");

        return ResponseResult.success();
    }

    public ResponseResult checkCode(VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationcode = verificationCodeDTO.getVerificationcode();
        // 根据手机号，从redis中取验证码
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        // 进行校验
        String value = stringRedisTemplate.opsForValue().get(key);
            // 若不存在  ||  若存在则看对不对的上
        if(StringUtils.isBlank(value) || !value.trim().equals(verificationcode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 对的上则根据手机号查询用户颁发token
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);

        // 将token存入redis
        String refreshTokenKey = RedisPrefixUtils.generatorKeyByToken(passengerPhone,
                                                        IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorKeyByToken(passengerPhone,
                                                         IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 60, TimeUnit.MINUTES);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.MINUTES);

        // 装入ResponseResult完成响应token
        TokenResponse tokenResponse = new TokenResponse(refreshToken, accessToken);
        System.out.println("refreshToken:" + tokenResponse.getRefreshToken());
        System.out.println("accessToken:" + tokenResponse.getAccessToken());

        return ResponseResult.success(tokenResponse);
    }



}
