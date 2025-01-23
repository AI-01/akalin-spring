package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从xml文件中加载BeanDefinition，并刷新上下文
     * @param configLocations xml文件路径
     */
    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
    }

    /**
     * 从xml文件中加载BeanDefinition，并刷新上下文
     * @param configLocations xml文件路径
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

}
