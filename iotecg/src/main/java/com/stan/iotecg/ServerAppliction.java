package com.stan.iotecg;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo
public class ServerAppliction {
    public static void main(String[] args) {

        // 服务提供者以非web 的方式启动
        new SpringApplicationBuilder(ServerAppliction.class)
                .web(WebApplicationType.NONE)
                .run(args);
        System.out.println("Server started.");
    }
}
