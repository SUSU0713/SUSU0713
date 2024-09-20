package com.mashibing.servicePrice.remote;


import com.mashibing.dto.ResponseResult;
import com.mashibing.request.ForecastPriceDTO;
import com.mashibing.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
