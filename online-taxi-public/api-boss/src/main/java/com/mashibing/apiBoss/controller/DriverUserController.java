package com.mashibing.apiBoss.controller;

import com.mashibing.apiBoss.service.DriverUserService;
import com.mashibing.dto.DriverUser;
import com.mashibing.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {

    @Autowired
    DriverUserService driverUserService;

    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody  DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody  DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }
}
