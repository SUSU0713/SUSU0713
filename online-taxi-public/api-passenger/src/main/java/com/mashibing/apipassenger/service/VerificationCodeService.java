package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class VerificationCodeService {
    public String generateCode(String passengerPhone){
        // 生成验证码
        String code = "1111";
        System.out.println("生成验证码："+code);
        // 存入redis
        System.out.println("存入redis");
        // 返回结果
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");
        return result.toString();
    }
}
