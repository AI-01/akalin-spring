package com.akalin.spring.beans.factory;

import com.akalin.spring.beans.BeansException;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
