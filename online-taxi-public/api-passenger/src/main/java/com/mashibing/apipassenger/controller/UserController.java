package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        return userService.getUserByAccessToken(accessToken);
    }
}
