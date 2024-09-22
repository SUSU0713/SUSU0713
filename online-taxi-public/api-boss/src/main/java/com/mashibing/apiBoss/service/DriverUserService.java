package com.mashibing.apiBoss.service;

import com.mashibing.apiBoss.remote.ServiceDriverUserClient;
import com.mashibing.dto.DriverUser;
import com.mashibing.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser){
        return serviceDriverUserClient.insertDriverInfo(driverUser);
    }

    public ResponseResult updateDriverUser(DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }
}
