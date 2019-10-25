package com.stan.iotbmp.config;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.stan.dubbobaseinterface.service.SyncDataHandleService;
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
//        return providerConfig;
//    }

    @Bean
    public ServiceBean<SyncDataHandleService> SyncDataHandleServiceExport(SyncDataHandleService serviceExport ){

        ServiceBean<SyncDataHandleService> serviceBean = new ServiceBean<SyncDataHandleService>();
        serviceBean.setProxy(JAVASSIST);
        serviceBean.setVersion(providerVersion);
        serviceBean.setInterface(SyncDataHandleService.class);
        serviceBean.setRef(serviceExport);
        serviceBean.setTimeout(TIMEOUT);
        serviceBean.setRetries(RETRIES);
        serviceBean.setGroup("iotbmp");
        return serviceBean;
    }
}
