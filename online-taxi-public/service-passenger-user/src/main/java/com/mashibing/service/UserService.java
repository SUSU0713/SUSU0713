package com.mashibing.service;

import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.dto.PassengerUser;
import com.mashibing.dto.ResponseResult;
import com.mashibing.mapper.PassengerUserMapper;
import com.mashibing.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone){
        // 根据手机号到数据库查询是否有用户
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
//        System.out.println(passengerUsers.size() == 0 ? "无记录" : passengerUsers.get(0).getPassengerPhone());
        // 无就注册
        if(passengerUsers.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("苏德福");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);

            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);

            passengerUserMapper.insert(passengerUser);
        }
        // 有就返回
        return ResponseResult.success();
    }

    public ResponseResult getUserByPhone(String passengerPhone){
        // 根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if(passengerUsers.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(), CommonStatusEnum.USER_NOT_EXIST.getValue());
        }

        PassengerUser passengerUser = passengerUsers.get(0);
        return ResponseResult.success(passengerUser);
    }
}
