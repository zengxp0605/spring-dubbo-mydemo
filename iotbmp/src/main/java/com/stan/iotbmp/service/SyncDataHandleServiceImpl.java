package com.stan.iotbmp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0", group = "iotbmp")
@Component
public class SyncDataHandleServiceImpl implements SyncDataHandleService {

    public JSONObject handleData(HandleDataModel hdm) {
        JSONObject obj = new JSONObject();
        obj.put("test", 123555);
        System.out.println("[iotbmp]-[handleData] obj:" + obj);
        return obj;
    }

    public String test(String str){
        String res = "[iotbmp]-[test] str:" + str;
        System.out.println(res);
        return res;
    }

    public String test2(String str){
        String res = "[iotbmp]-[test2] str:" + str;
        System.out.println(res);
        return res;
    }
}
