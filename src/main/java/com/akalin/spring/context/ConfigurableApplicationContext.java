package com.akalin.spring.context;

import com.akalin.spring.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeansException bean异常
     */
    void refresh() throws BeansException;

    /**
     * 注册销毁钩子
     */
    void registerShutdownHook();

    /**
     * 关闭容器
     */
    void close();

}
