package com.stan.gateway.config;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.stan.dubbobaseinterface.service.HelloService;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
import com.stan.dubbobaseinterface.service.WebEntryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportServiceConfig extends DubboBaseConfig {

    private static final int TIMEOUT = 5000;
    private static final int RETRIES = 0;
    private static final String JAVASSIST = "javassist";

    @Value("${dubbo.protocol.port}")
    private int port;

    @Value("${dubbo.provider.version}")
    private String providerVersion;

    @Value("${dubbo.consumer.version}")
    private String consumerVersion;

    @Value("${dubbo.consumer.timeout}")
    private int consumerTimeOut;

    private boolean commonCheck = false;

    private int commonRetires = 0;

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(port);
        protocolConfig.setThreads(600); // ???
        return protocolConfig;
    }

//    @Bean
//    public ProviderConfig providerConfig(){
//        ProviderConfig providerConfig = new ProviderConfig();
//
//        return providerConfig;
//    }

    @Bean
    public ServiceBean<WebEntryService> WebEntryServiceExport(WebEntryService serviceExport){

        ServiceBean<WebEntryService> serviceBean = new ServiceBean<WebEntryService>();
        serviceBean.setProxy(JAVASSIST);
        serviceBean.setVersion(providerVersion);
        serviceBean.setInterface(WebEntryService.class);
        serviceBean.setRef(serviceExport);
        serviceBean.setTimeout(TIMEOUT);
        serviceBean.setRetries(RETRIES);
        return serviceBean;
    }

    /**
     * 另外一个 `@Reference(version = "1.0.0", group = "iotbmp-hello")` 的实现方式
     * 调用时需要使用 `@Autowired` 注解
     * @return
     */
    @Bean
    public ReferenceBean<HelloService> helloService(){
        ReferenceBean<HelloService> referenceBean = new ReferenceBean<HelloService>();
        referenceBean.setVersion(providerVersion);
        referenceBean.setInterface(HelloService.class);
        referenceBean.setCheck(commonCheck);
        referenceBean.setTimeout(consumerTimeOut);
        referenceBean.setRetries(commonRetires);
        referenceBean.setGroup("iotbmp-hello");
        return referenceBean;
    }
}
