package com.ecnu.traceability.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class OneNETService {
    private Map<String, JSONObject> realTimeLocationMap = new HashMap<>();


    public void addLocation(String deviceId,JSONObject jsonObject){
        realTimeLocationMap.put(deviceId, jsonObject);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendRealtimeLocation() {
        if (realTimeLocationMap.size() > 0) {//有实时数据
            JSONArray array = new JSONArray();
            for (Map.Entry entry : realTimeLocationMap.entrySet()) {
                array.add(entry.getValue());
            }
//            OneNETDevice.pushRealTimeLocation("598576209", array);
        }
    }

}
