package com.baizhi.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext Context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.Context = applicationContext;
    }
    //    根据beanId获取对象
    public static Object getBean(String id) {
        return Context.getBean(id);
    }
//    根据type获取对象
    public static Object getBean(Class clazz) {
        return Context.getBean(clazz);
    }
//    根据beanId和type获取对象
    public static Object getBean(String id,Class clazz) {
        return Context.getBean(id,clazz);
    }
}
