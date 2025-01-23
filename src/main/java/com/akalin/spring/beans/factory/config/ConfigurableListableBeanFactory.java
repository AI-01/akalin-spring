package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.ListableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory , ConfigurableBeanFactory {

    void preInstantiateSingletons() throws BeansException;

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
