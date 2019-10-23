package com.stan.iotbmp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stan.dubbobaseinterface.service.HelloService;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0", group = "iotbmp-hello")
@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String s) {
        return "[HelloServiceImpl-sayHello] " + s;
    }

    @Override
    public String sayGoodbye(String s) {
        return "[HelloServiceImpl-sayGoodbye] " + s;
    }
}
