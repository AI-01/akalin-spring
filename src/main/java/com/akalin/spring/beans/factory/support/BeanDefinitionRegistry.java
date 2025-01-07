package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * 注册bean定义
     * @param beanName bean名称
     * @param beanDefinition bean定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 是否包含bean定义
     * @param beanName bean名称
     * @return true-存在 false-不存在
     */
    boolean containsBeanDefinition(String beanName);

}
