package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁容器中所有的单例bean
     */
    void destroySingletons();
}
