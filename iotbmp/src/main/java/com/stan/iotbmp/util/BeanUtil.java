package com.stan.iotbmp.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String className) {
        System.out.println("[getBean]" + className);
        return applicationContext.getBean(className);
    }

    public static <T> T getBean(String className, Class<T> clazz) {
        System.out.println("[getBean2]" + className);
        return applicationContext.getBean(className, clazz);
    }
}
