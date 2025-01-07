package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {

    /**
     * 初始化bean
     * @param beanDefinition bean定义
     * @param beanName bean名称
     * @param constructor bean构造函数
     * @param args bean构造函数参数
     * @return bean实例
     * @throws BeansException bean异常
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException;

}
