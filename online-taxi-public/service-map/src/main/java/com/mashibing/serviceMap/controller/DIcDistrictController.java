package com.mashibing.serviceMap.controller;

import com.mashibing.dto.ResponseResult;
import com.mashibing.serviceMap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DIcDistrictController {

    @Autowired
    DicDistrictService dicDistrictService;

    @GetMapping("/dis-district")
    public ResponseResult initDicDistrict(String keywords){
        return dicDistrictService.initDicDistrict(keywords);
    }

}
