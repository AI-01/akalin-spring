package com.akalin.spring.beans.factory;


import com.akalin.spring.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取Bean集合
     * @param type Bean类型
     * @return Bean集合
     * @param <T> bean泛型
     * @throws BeansException bean异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 获取BeanDefinitionName数组
     * @return BeanDefinitionName数组
     */
    String[] getBeanDefinitionNames();

}
