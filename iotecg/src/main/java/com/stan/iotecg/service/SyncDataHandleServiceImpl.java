package com.stan.iotecg.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0", group = "iotecg")
@Component
public class SyncDataHandleServiceImpl implements SyncDataHandleService {

    public JSONObject handleData(HandleDataModel hdm) {
        JSONObject obj = new JSONObject();
        obj.put("test", 23244);
        obj.put("from-fun", "iotecg.handleData");
        System.out.println("[iotecg]-[handleData] obj:" + obj);
        return obj;
    }

    public String test(String str){
        String res = "[iotecg]-[test] str:" + str;
        System.out.println(res);
        return res;
    }

    public String test2(String str){
        String res = "[iotecg]-[test2] str:" + str;
        System.out.println(res);
        return res;
    }
}
