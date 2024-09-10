package com.mashibing.apipassenger.controller;

import com.mashibing.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/test")
    public static String test(){
        return "test success";
    }

    @GetMapping("/authTest")
    public static ResponseResult authTest(){
        System.out.println("authTest成功通过拦截器");
        return ResponseResult.success();
    }

    @GetMapping("/noAuthTest")
    public static ResponseResult noAuthTest(){
        System.out.println("noAuthTest成功通过拦截器");
        return ResponseResult.success();
    }

}
