package com.akalin.spring.beans.factory;

import com.akalin.spring.beans.BeansException;

public interface BeanFactory {

    /**
     * 获取bean实例
     * @param name bean名称
     * @return bean实例
     * @throws BeansException bean异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取bean实例（bean构造函数带有参数）
     * @param name bean名称
     * @param args bean构造函数参数
     * @return bean实例
     * @throws BeansException bean异常
     */
    Object getBean(String name, Object... args) throws BeansException;

}
