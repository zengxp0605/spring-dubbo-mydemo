package com.stan.dubbobaseinterface.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class HandleDataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cmd;
    private JSONObject data;

}
