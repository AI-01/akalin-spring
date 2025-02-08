package com.akalin.spring.beans.factory;

public interface FactoryBean<T> {

    /**
     * 获取对象
     */
    T getObject() throws Exception;

    /**
     * 获取对象类型
     */
    Class<?> getObjectType();

    /**
     * 是否单例
     */
    boolean isSingleton();

}
