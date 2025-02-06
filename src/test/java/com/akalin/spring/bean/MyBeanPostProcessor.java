package com.akalin.spring.bean;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanPostProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        log.info("MyBeanPostProcessor init");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("Before Initialization: " + beanName);
        if (beanName.equals("userService")) {
            UserService userService = (UserService) bean;
            userService.setCompany("akalin");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("After Initialization: " + beanName);
        return bean;
    }
}
