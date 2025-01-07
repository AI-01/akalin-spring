package com.akalin.spring.beans.factory.config;

public interface SingletonBeanRegistry {

    /**
     * 获取单例bean
     * @param beanName bean名称
     * @return 单例bean
     */
    Object getSingleton(String beanName);

}
