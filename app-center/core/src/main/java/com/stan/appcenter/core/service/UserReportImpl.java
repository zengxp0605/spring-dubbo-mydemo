package com.stan.appcenter.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.appcenter.client.dubboExport.UserReport;

@Service
public class UserReportImpl implements UserReport {

    public JSONObject uploadData (String params){
        JSONObject res = new JSONObject();
        res.put("code", 0);

        return res;
    }
}
