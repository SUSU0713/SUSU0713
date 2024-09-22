package com.mashibing.serviceDriverUser.controller;

import com.mashibing.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test")
    public ResponseResult testtest(Integer num){
        return ResponseResult.success();
    }
}
