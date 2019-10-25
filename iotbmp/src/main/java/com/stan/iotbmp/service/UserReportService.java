package com.stan.iotbmp.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserReportService {

    public JSONObject uploadData(JSONObject params){
        System.out.println("[UserReportService.uploadData]上传数据参数: " + JSONObject.toJSONString(params));

        return success("[uploadData]上传成功", params);
    }

    public JSONObject test(JSONObject params){
        System.out.println("[UserReportService.test]test参数: " + JSONObject.toJSONString(params));
        return success("[test]上传成功", params);
    }

    private JSONObject success(String msg, JSONObject data){
        JSONObject res = new JSONObject();
        res.put("code", 200);
        res.put("res", data);
        res.put("msg", msg);
        return res;
    }
}
