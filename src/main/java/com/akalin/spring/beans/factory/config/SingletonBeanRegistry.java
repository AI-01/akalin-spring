package com.akalin.spring.beans.factory.config;

public interface SingletonBeanRegistry {

    /**
     * 获取单例bean
     * @param beanName bean名称
     * @return 单例bean
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例bean
     * @param beanName bean名称
     * @param singletonObject 单例bean
     */
    void registerSingleton(String beanName, Object singletonObject);

}
