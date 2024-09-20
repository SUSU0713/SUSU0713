package com.mashibing.apipassenger.remote;

import com.mashibing.dto.ResponseResult;
import com.mashibing.request.ForecastPriceDTO;
import com.mashibing.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/forecast-price")
    public ResponseResult forecast(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
