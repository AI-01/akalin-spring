package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.BeansException;

public interface BeanFactoryPostProcessor {

    /**
     * 所有BeanDefinition加载完成后，Bean对象实例化之前，提供修改BeanDefinition属性的机会
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;


}
