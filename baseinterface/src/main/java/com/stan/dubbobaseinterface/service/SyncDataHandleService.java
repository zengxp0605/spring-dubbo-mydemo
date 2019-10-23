package com.stan.dubbobaseinterface.service;

import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;

public interface SyncDataHandleService {
    JSONObject handleData(HandleDataModel handleDataModel);

    String test(String str);

    String test2(String str);
}
