package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePriceClient;
import com.mashibing.dto.ResponseResult;
import com.mashibing.request.ForecastPriceDTO;
import com.mashibing.response.ForecastPriceResponse;
import feign.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {
    @Autowired
    ServicePriceClient servicePriceClient;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        log.info("出发地经度: "+depLongitude);
        log.info("出发地经度: "+depLatitude);
        log.info("出发地经度: "+destLongitude);
        log.info("出发地经度: "+destLatitude);
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        return servicePriceClient.forecast(forecastPriceDTO);
//        return ResponseResult.success();
    }
}
