package com.akalin.spring.bean;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.PropertyValue;
import com.akalin.spring.beans.PropertyValues;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.akalin.spring.beans.factory.config.ConfigurableListableBeanFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        log.info("MyBeanFactoryPostProcessor init");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("location", "shanghai"));
    }

}
