package com.stan.gateway.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.stan.dubbobaseinterface.entity.HandleDataModel;
import com.stan.dubbobaseinterface.service.HelloService;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import com.stan.dubbobaseinterface.service.WebEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

// 这里不用注解实现的话, 需要在ExportServiceConfig 中配置
//@Service(version = "1.0.0")
@Component
public class WebEntryServiceImpl implements WebEntryService {

    // 这里是测试另外一个group 分组的使用
    @Reference(version = "1.0.0", group = "iotbmp-hello")
    HelloService helloService;

    // 另外一种实现方式,配合配置@Bean
//    @Autowired
//    HelloService helloService;

    // 普通调用入口
    public String entry(String groupName, String methodName, String paramId) {
        String time = TimeZone.getDefault().getDisplayName() + ": "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());

        testIotBmpGroup();
//        return time;

        // 普通调用
        JSONObject result = reportData(groupName, methodName, paramId);
        JSONObject obj = new JSONObject();
        obj.put("result", result);
        obj.put("class.fun", "WebEntryServiceImpl.entry");
        obj.put("groupName", groupName);
        obj.put("time", time);

        return JSONObject.toJSONString(obj);
    }

    // 泛化调用入口
    public String entryGeneric(String groupName, String methodName, String paramId) {
        String time = TimeZone.getDefault().getDisplayName() + ": "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());

        // 泛化调用
        Object result = genericReportData(groupName, methodName, paramId);

        JSONObject obj = new JSONObject();
        obj.put("result", result);
        obj.put("class.fun", "WebEntryServiceImpl.entryGeneric");
        obj.put("groupName", groupName);
        obj.put("time", time);
        obj.put("remark", "这个的泛化调用的结果");

        return JSONObject.toJSONString(obj);
    }

    /**
     * 普通调用
     * 通过 groupName 和 methodName 方法名调用不同dubbo服务的对应接口
     * @return
     */
    private JSONObject reportData(String groupName, String methodName, String paramId){
        System.out.println("[reportData]" + groupName + " || " + methodName + " || " + paramId);

        DubboServiceFactory dubboServiceFactory = DubboServiceFactory.getInstance();
        SyncDataHandleService syncDataHandleService = dubboServiceFactory.getInvokeService(groupName);
        HandleDataModel handleDataModel = new HandleDataModel();

        JSONObject data = new JSONObject();
        data.put("id", paramId);
        data.put("methodName", methodName);
        data.put("detail", "{\"reportId\": \"124\", \"weight\": 61.5}");
        handleDataModel.setData(data);
        handleDataModel.setCmd(methodName);
        JSONObject obj= syncDataHandleService.handleData(handleDataModel);

        System.out.println("handleData result: " + obj);

        return obj;
    }

    /**
     * 泛化调用
     * 通过 groupName 和 methodName 方法名调用不同dubbo服务的对应接口
     * @return
     */
    private Object genericReportData(String groupName, String methodName, String paramId){
        System.out.println("[genericReportData]" + groupName + " || " + methodName + " || " + paramId);

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
