package com.stan.gateway.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;

import java.util.List;
import java.util.Map;

public class DubboServiceFactory {

    private ApplicationConfig application;
    private RegistryConfig registry;

    private String appName = "web-gateway-provider";

    private  String dubboAddress = "jasonzeng.top:2181";

    private static class SingletonHolder {
        private static DubboServiceFactory INTANCE = new DubboServiceFactory();
    }

    private DubboServiceFactory(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(appName);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboAddress);

        System.out.println("==========" + appName + " || " + dubboAddress);

        this.application = applicationConfig;
        this.registry = registryConfig;
    }

    public static DubboServiceFactory getInstance(){
        return SingletonHolder.INTANCE;
    }

    /**
     * 泛化调用
     * @param groupName
     * @param methodName
     * @param parameters
     * @return
     */
    public Object genericInvoke(String groupName, String methodName, List<Map<String, Object>> parameters){

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setGroup(groupName);
//        reference.setInterface(interfaceClass); // 接口名
        reference.setInterface(SyncDataHandleService.class); // 接口名 // Class interfaceClass
        reference.setGeneric(true); // 声明为泛化接口
        reference.setVersion("1.0.0");

        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
//
        int len = parameters.size();
        String[] invokeParamTyeps = new String[len];
        Object[] invokeParams = new Object[len];
        for(int i = 0; i < len; i++){
            invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
            invokeParams[i] = parameters.get(i).get("Object");
        }

        System.out.println("genericInvoke:=----" + methodName + " || " + invokeParamTyeps + " || " + invokeParams);
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
    }

    // 普通调用
    public SyncDataHandleService getInvokeService(String groupName){
        ReferenceConfig<SyncDataHandleService> reference = new ReferenceConfig<SyncDataHandleService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setGroup(groupName);
        reference.setInterface(SyncDataHandleService.class);
//        reference.setGeneric(true);
        reference.setVersion("1.0.0"); // TODO: 动态获取
        return reference.get();
//        // TODO: 可以优化为下面的从缓存中获取, 但是会与上面的 `genericInvoke` 方法产生的缓存冲突??
//        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
//        return cache.get(reference);
    }
}
