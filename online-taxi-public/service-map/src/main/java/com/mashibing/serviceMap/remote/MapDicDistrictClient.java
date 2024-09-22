package com.mashibing.serviceMap.remote;

import com.mashibing.constant.AmapConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MapDicDistrictClient {
    @Value("${amap.key}")
    private String key;

    @Autowired
    RestTemplate restTemplate;

    public String disDistrict(String keywords){
        StringBuilder URL = new StringBuilder(AmapConfigConstants.DISTRICTS_URL);
        URL.append("key=" + key);
        URL.append("&address=" + keywords);
        URL.append("&subdistrict=3");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL.toString(), String.class);
        // 解析返回结果
        String response = responseEntity.getBody();
        return response;
    }
}
