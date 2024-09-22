package com.mashibing.serviceDriverUser.controller;


import com.mashibing.dto.ResponseResult;
import com.mashibing.dto.DriverUser;
import com.mashibing.serviceDriverUser.service.impl.DriverUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2024-09-22
 */
@RestController
@Slf4j
public class DriverController {

    @Autowired
    DriverUserServiceImpl driverUserService;

    @PostMapping("/user")
    public ResponseResult insertDriverInfo(@RequestBody  DriverUser driverUser){
        log.info("插入司机信息：" + driverUser.toString());
        return driverUserService.insertDriverInfo(driverUser);
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody  DriverUser driverUser){
        log.info("修改司机信息：" + driverUser.toString());
        return driverUserService.updateDriverInfo(driverUser);
    }
}
