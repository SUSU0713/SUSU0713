package com.mashibing.apiDriver.service;

import com.mashibing.apiDriver.remote.ServiceDriverUserClient;
import com.mashibing.dto.DriverUser;
import com.mashibing.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateDriverUser(DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }
}
