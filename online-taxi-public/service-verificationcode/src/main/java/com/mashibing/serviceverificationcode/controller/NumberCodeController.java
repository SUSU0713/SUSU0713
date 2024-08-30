package com.mashibing.serviceverificationcode.controller;

import com.mashibing.dto.ResponseResult;
import com.mashibing.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
        double randomNumber =  ( Math.random()*9 + 1 ) * Math.pow(10, size);
        int code = (int) randomNumber;
        NumberCodeResponse data = new NumberCodeResponse();
        data.setNumberCode(code);
        return ResponseResult.success(data);
    }
}
