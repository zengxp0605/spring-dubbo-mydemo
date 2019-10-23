package com.stan.appcenter.client.dubboExport;

import com.alibaba.fastjson.JSONObject;

public interface UserReport {
    JSONObject uploadData(String params);
}
