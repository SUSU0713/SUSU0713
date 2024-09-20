package com.mashibing.serviceMap.service;

import com.mashibing.dto.ResponseResult;
import com.mashibing.response.DirectionResponse;
import com.mashibing.serviceMap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    @Autowired
    MapDirectionClient mapDirectionClient;

    public ResponseResult<DirectionResponse> driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){

        DirectionResponse directionResponse = mapDirectionClient.direction( depLongitude,  depLatitude,  destLongitude,  destLatitude);
        return ResponseResult.success(directionResponse);
    }
}
