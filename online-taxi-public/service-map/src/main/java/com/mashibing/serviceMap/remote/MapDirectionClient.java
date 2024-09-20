package com.mashibing.serviceMap.remote;

import com.mashibing.constant.AmapConfigConstants;
import com.mashibing.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        StringBuilder URL = new StringBuilder();
        URL.append(AmapConfigConstants.DIRECTION_URL);
        URL.append("origin=");
        URL.append(depLongitude + "," + depLatitude);
        URL.append("&destination=");
        URL.append(destLongitude + "," + destLatitude);
        URL.append("&extensions=all");
        URL.append("&output=JSON");
        URL.append("&key="+amapKey);
        log.info(URL.toString());

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(URL.toString(), String.class);
        log.info("高德地图：路径规划，返回信息"+directionEntity.getBody());

        return parseDirectionEntity(directionEntity.getBody());
    }

    public DirectionResponse parseDirectionEntity(String directionString){
        DirectionResponse directionResponse = null;
        try{
            directionResponse = new DirectionResponse();
            JSONObject result = JSONObject.fromObject(directionString);
            if(result.has(AmapConfigConstants.STATUS) && result.getInt(AmapConfigConstants.STATUS) == 1 &&
                    result.has(AmapConfigConstants.ROUTE)){
                JSONObject routeResult = result.getJSONObject(AmapConfigConstants.ROUTE);
                JSONArray pathResults = routeResult.getJSONArray(AmapConfigConstants.PATHS);
                JSONObject pathResult = pathResults.getJSONObject(0);
                if(pathResult.has(AmapConfigConstants.DISTANCE) && pathResult.has(AmapConfigConstants.DURATION)){
                    int distance = pathResult.getInt(AmapConfigConstants.DISTANCE);
                    int duration = pathResult.getInt(AmapConfigConstants.DURATION);
                    directionResponse.setDistance(distance);
                    directionResponse.setDuration(duration);
                }

            }

        }catch (Exception e){
            log.error("高德地图API返回字符串解析错误");
            e.printStackTrace();
        }

        return directionResponse;
    }

//    public static void main(String[] args) {
//        MapDirectionClient mapDirectionClient = new MapDirectionClient();
//        mapDirectionClient.direction("116.481028", "39.989643", "116.465302", "40.004717");
//    }
}
