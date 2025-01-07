package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 简单实例化策略（JDK实例化）
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException {
        Class beanClass = beanDefinition.getBeanClass();
        Object bean;
        try {
            if (null != constructor) {
                // 有参构造函数初始化
                bean = beanClass.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            }else {
                // 无参构造函数初始化
                bean = beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        return bean;
    }

}
