package com.mashibing.servicePrice.controller;

import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.dto.PriceRule;
import com.mashibing.dto.ResponseResult;
import com.mashibing.request.ForecastPriceDTO;
import com.mashibing.response.DirectionResponse;
import com.mashibing.response.ForecastPriceResponse;
import com.mashibing.servicePrice.mapper.PriceRuleMapper;
import com.mashibing.servicePrice.remote.ServiceMapClient;
import com.mashibing.servicePrice.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ForecastController {
    @Autowired
    PriceRuleMapper priceRuleMapper;

    @Autowired
    ServiceMapClient serviceMapClient;

    @Autowired
    ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecast(@RequestBody ForecastPriceDTO forecastPriceDTO){

        log.info("调用地图服务，查询距离和时长");
        ResponseResult<DirectionResponse> directionResponse = serviceMapClient.driving(forecastPriceDTO);
        DirectionResponse direction = directionResponse.getData();
        Integer distance = direction.getDistance();
        Integer duration = direction.getDuration();


        log.info("读取计价规则");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code", "110000");
        queryMap.put("vehicle_type", "1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离、时长和计价规则，计算价格");
        double price = forecastPriceService.getPrice(priceRule, distance, duration);


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }
}
