package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.BeansException;

public interface AutowireCapableBeanFactory {

    /**
     * 对已经存在的 Bean 实例进行初始化
     * @param existingBean 已经存在的 Bean 实例
     * @param beanName Bean 的名称
     * @param beanDefinition Bean 定义
     * @return 初始化后的 Bean 实例
     * @throws BeansException bean异常
     */
    Object initializeBean(Object existingBean, String beanName, BeanDefinition beanDefinition) throws BeansException;

}
