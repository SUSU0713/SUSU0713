package com.mashibing.servicePrice.service;

import com.mashibing.dto.PriceRule;
import com.mashibing.utils.BigDecimalUtils;
import org.springframework.stereotype.Service;

@Service
public class ForecastPriceService {

    public double getPrice(PriceRule priceRule, Integer distance, Integer duration){
        if(distance <= priceRule.getStartMile()){
            return priceRule.getStartFare();
        }

        double exceedMile = BigDecimalUtils.subtract(BigDecimalUtils.divide(distance, 1000), priceRule.getStartMile());
        double mileSpend = priceRule.getStartFare() + BigDecimalUtils.multiply(exceedMile, priceRule.getUnitPricePerMile());
        double minuteSpend = BigDecimalUtils.multiply(BigDecimalUtils.divide(duration, 60), priceRule.getUnitPricePerMinute());

        return BigDecimalUtils.add(mileSpend, minuteSpend);
    }

}
