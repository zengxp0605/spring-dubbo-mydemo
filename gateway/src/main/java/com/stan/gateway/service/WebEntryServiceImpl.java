package com.stan.gateway.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;
import com.stan.dubbobaseinterface.service.HelloService;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import com.stan.dubbobaseinterface.service.WebEntryService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(version = "1.0.0")
@Component
public class WebEntryServiceImpl implements WebEntryService {

    // 这里是测试另外一个group 分组的使用
    @Reference(version = "1.0.0", group = "iotbmp-hello")
    HelloService helloService;

    public String entry(String groupName, String methodName, String paramId) {
        String time = TimeZone.getDefault().getDisplayName() + ": "
                + new SimpleDateFormat("Y年M月d日H时m分s秒").format(System.currentTimeMillis());

//        testIotBmpGroup();
//        return time;

        Object obj = reportData(groupName, methodName, paramId);

        return "[WebEntryServiceImpl.entry] groupName: " + groupName + " ,result object: " + obj + "<br/>" + time;
    }

    /**
     * 通过 groupName 和 methodName 方法名调用不同dubbo服务的对应接口
     * @return
     */
    private Object reportData(String groupName, String methodName, String paramId){
        System.out.println("[reportData]" + groupName + " || " + methodName + " || " + paramId);

        DubboServiceFactory dubboServiceFactory = DubboServiceFactory.getInstance();

//        String interfaceName = groupName;
//        Class interfaceName =SyncDataHandleService.class;// "com.stan.dubbobaseinterface.service.SyncDataHandleService";

        Map map = new HashMap<String, String>();
        map.put("ParamType", "java.lang.String");  //后端接口参数类型
        map.put("Object", paramId);  //用以调用后端接口的实参

        List<Map<String, Object>> paramInfos= new ArrayList<Map<String, Object>>();
        paramInfos.add(map);

        Object obj = dubboServiceFactory.genericInvoke(groupName, methodName, paramInfos);

        System.out.println("invoke result: " + obj);

        return obj;
    }

    /**
     * 通过 @Reference(version = "1.0.0") 消费者的方式调用
     * - 这时 provider 不使用group
     */
    private void testIotBmpGroup(){
        HandleDataModel hmd = new HandleDataModel();
        hmd.setCmd("testCmd");
        JSONObject obj = new JSONObject();
        obj.put("param1", 1);
        hmd.setData(obj);
        String str = helloService.sayHello(hmd.toString());
        System.out.println("[testIotBmpGroup] str: " + str);
    }

}
