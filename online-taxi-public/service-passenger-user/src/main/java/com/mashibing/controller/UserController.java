package com.mashibing.controller;


import com.mashibing.dto.ResponseResult;
import com.mashibing.request.VerificationCodeDTO;
import com.mashibing.response.TokenResponse;
import com.mashibing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String phone = verificationCodeDTO.getPassengerPhone();
        System.out.println(phone);
        return userService.loginOrRegister(phone);
    }
}
