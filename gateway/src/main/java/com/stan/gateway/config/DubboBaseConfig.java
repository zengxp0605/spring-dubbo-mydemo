package com.stan.gateway.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class DubboBaseConfig {

    private static final String PROTOCOL = "zookeeper";

    private static final String REGISTRY = "registry";

    @Value("${dubbo.registry.address}")
    private String dubboRegistryUrl;

    @Value("${dubbo.application.name}")
    private String appName;

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboRegistryUrl);
        registryConfig.setProtocol(PROTOCOL);
        return registryConfig;
    }

//    @Bean
//    public ApplicationConfig application(){
//        return new ApplicationConfig(appName);
//    }

}
