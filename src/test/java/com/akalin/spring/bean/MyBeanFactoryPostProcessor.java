package com.akalin.spring.bean;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.PropertyValue;
import com.akalin.spring.beans.PropertyValues;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.akalin.spring.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("age", 18));
    }

}
