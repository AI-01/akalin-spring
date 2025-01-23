package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.BeansException;

public interface BeanPostProcessor {


    /**
     * 在Bean对象初始化之前，提供修改Bean属性的机会
     * @param bean bean对象
     * @param beanName bean对象名称
     * @return 修改后的bean对象
     * @throws BeansException bean异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在Bean对象初始化之后，提供修改Bean属性的机会
     * @param bean bean对象
     * @param beanName bean对象名称
     * @return 修改后的bean对象
     * @throws BeansException bean异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
