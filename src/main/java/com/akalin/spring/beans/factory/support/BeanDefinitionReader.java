package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.core.io.Resource;
import com.akalin.spring.core.io.ResourceLoader;
import org.springframework.beans.factory.BeanDefinitionStoreException;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
