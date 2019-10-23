package com.stan.appcenter.client.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.stan.appcenter.client.dubboExport.UserReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    public static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Reference
    private UserReport userReport;

    @GetMapping("/upload")
    public String upload(@RequestParam String data){

        logger.info("接收数据: {}", data);

        JSONObject res = userReport.uploadData(data);

        logger.info("返回结果: {}", res);

        return "upload";
    }

    @GetMapping("/test")
    public String test(@RequestParam String data){
        JSONObject reqData = JSONObject.parseObject(data);
        logger.info("访问test接口: data={}", reqData);

        return "test";
    }
}
