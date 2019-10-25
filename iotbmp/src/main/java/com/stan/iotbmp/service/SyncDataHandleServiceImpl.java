package com.stan.iotbmp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import com.stan.iotbmp.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

// 去掉注解, 在ExportServiceConfig 中实现接口暴露
//@Service(version = "1.0.0", group = "iotbmp")
@Component
public class SyncDataHandleServiceImpl implements SyncDataHandleService {

    private static final Logger logger = LoggerFactory.getLogger(SyncDataHandleService.class);

    public JSONObject handleData(HandleDataModel hdm) {
        logger.info("[iotbmp]-[handleData] hdm: {}" , hdm.toString());

        HashMap<String, String[]> handleType = new HashMap<String, String[]>();

        handleType.put("uploadData", new String[] {"userReportService", "uploadData"});
        handleType.put("test", new String[] {"userReportService", "test"});
        handleType.put("test2", new String[] {"syncDataHandleServiceImpl", "test2"});

        logger.info("[iotbmp]-[handleData] 方法: {},入参: {}" , hdm.getCmd(), hdm.getData() );
        JSONObject result= new JSONObject();

        try {
            String cmd = hdm.getCmd();
            String[] arr = handleType.get(cmd);
            String className = arr[0];
            String methodName = arr[1];
            Object cls = BeanUtil.getBean(className);
            Method method = cls.getClass().getMethod(methodName, JSONObject.class);
            Object res = method.invoke(cls, hdm.getData());
            result = (JSONObject) res;
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
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

    /**
     * 这个方法是通过 handleData 间接调用的, 形参类型和上面那个 test2不一样
     * @param obj
     * @return
     */
    public JSONObject test2(JSONObject obj){
        String res = "[iotbmp]-[test2] JSONObject:" + obj;
        System.out.println(res);
        obj.put("__from", "[iotbmp]-[test2] JSONObject");
        return obj;
    }
}
