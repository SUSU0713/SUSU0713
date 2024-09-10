package com.mashibing.apipassenger.service;

import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.constant.IdentityConstant;
import com.mashibing.constant.TokenConstant;
import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.TokenResult;
import com.mashibing.response.TokenResponse;
import com.mashibing.utils.JwtUtils;
import com.mashibing.utils.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc){
        // 解析refreshToken
        TokenResult refreshToken = JwtUtils.checkToken(refreshTokenSrc);
        // 查询refreshToken
        String tokenRedisKey = RedisPrefixUtils.generatorKeyByToken(refreshToken.getPhone(), refreshToken.getIdentity(),
                                                                                    TokenConstant.REFRESH_TOKEN_TYPE);
        // 校验refreshToken
        String value = stringRedisTemplate.opsForValue().get(tokenRedisKey);
        // 若不存在  ||  若存在则看对不对的上
        if(StringUtils.isBlank(value) || !value.trim().equals(refreshTokenSrc.trim())) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),
                    CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        // 生成新的双Token返回
        String newRefreshToken = JwtUtils.generatorToken(refreshToken.getPhone(), refreshToken.getIdentity(), TokenConstant.REFRESH_TOKEN_TYPE);
        String newAccessToken = JwtUtils.generatorToken(refreshToken.getPhone(), refreshToken.getIdentity(), TokenConstant.ACCESS_TOKEN_TYPE);
        TokenResponse tokenResponse = new TokenResponse(newRefreshToken, newAccessToken);

        //存入redis
        String accessTokenKey = RedisPrefixUtils.generatorKeyByToken(refreshToken.getPhone(),
                refreshToken.getIdentity(), TokenConstant.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, newAccessToken,30, TimeUnit.MINUTES);
        stringRedisTemplate.opsForValue().set(tokenRedisKey, newRefreshToken, 60, TimeUnit.MINUTES);

        return ResponseResult.success(tokenResponse);
    }
}
