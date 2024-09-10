package com.mashibing.apipassenger.remote;

import com.mashibing.dto.PassengerUser;
import com.mashibing.dto.ResponseResult;
import com.mashibing.request.VerificationCodeDTO;
import com.mashibing.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String phone);
}
