package com.akalin.spring.context;

import com.akalin.spring.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeansException bean异常
     */
    void refresh() throws BeansException;

}
