package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.dto.PassengerUser;
import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.TokenResult;
import com.mashibing.response.TokenResponse;
import com.mashibing.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;


    public ResponseResult getUserByAccessToken(String accessToken){
        // 解析token，获取手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        // 通过手机号向Service-passenger-user微服务查询用户信息
        // 返回查询到的用户信息
        return servicePassengerUserClient.getUser(phone);

    }

}
