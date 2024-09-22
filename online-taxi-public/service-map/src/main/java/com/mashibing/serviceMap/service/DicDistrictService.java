package com.mashibing.serviceMap.service;

import com.mashibing.constant.AmapConfigConstants;
import com.mashibing.constant.CommonStatusEnum;
import com.mashibing.dto.DicDistrict;
import com.mashibing.dto.ResponseResult;
import com.mashibing.serviceMap.mapper.DicDistrictMapper;
import com.mashibing.serviceMap.remote.MapDicDistrictClient;
import com.mashibing.serviceMap.remote.MapDirectionClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DicDistrictService {
    @Autowired
    MapDicDistrictClient mapDicDistrictClient;
    @Autowired
    DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords){
        // 拼接URL请求字符串
        // 解析返回结果
        String response = mapDicDistrictClient.disDistrict(keywords);
        JSONObject res = JSONObject.fromObject(response);
        if(!(res.has(AmapConfigConstants.STATUS ) && "1".equals(res.getString(AmapConfigConstants.STATUS)))){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
         // 插入数据库
        if(!parseAndInsert(res.getJSONArray(AmapConfigConstants.DISTRICT),  null)){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }

        return ResponseResult.success();
    }

    public boolean parseAndInsert(JSONArray districts, String parent){
        if(districts == null ){
            return false;
        }
        if(districts.size() == 0){
            return true;
        }
        boolean finish = false;
        for(int i = 0; i < districts.size(); i++){
            JSONObject sub = districts.getJSONObject(i);
            String levelString = sub.getString(AmapConfigConstants.LEVEL);
            int level = parseLevel(levelString);
            if(level == -1){
                continue;
            }
            String adcode = sub.getString(AmapConfigConstants.ADCODE);
            String name = sub.getString(AmapConfigConstants.NAME);

            DicDistrict dicDistrict = new DicDistrict();
            dicDistrict.setAddressCode(adcode);
            dicDistrict.setAddressName(name);
            dicDistrict.setLevel(level);
            dicDistrict.setParentAddressCode(parent);
            dicDistrictMapper.insert(dicDistrict);

            finish |= parseAndInsert(sub.getJSONArray(AmapConfigConstants.DISTRICT),  adcode);
        }
        return finish;
    }

    public int parseLevel(String level){
        int res = -1;
        switch (level){
            case "country" : res = 0; break;
            case "province" : res = 1; break;
            case "city" : res = 2; break;
            case "district" : res = 3; break;
        }
        return res;
    }
}
