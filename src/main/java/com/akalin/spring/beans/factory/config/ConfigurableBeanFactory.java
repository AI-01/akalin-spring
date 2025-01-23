package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
