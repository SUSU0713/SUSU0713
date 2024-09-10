package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.dto.ResponseResult;
import com.mashibing.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tokenController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult  refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        return tokenService.refreshToken(refreshToken);
    }
}
