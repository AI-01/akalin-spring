package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.akalin.spring.beans.factory.config.BeanPostProcessor;
import com.akalin.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.akalin.spring.context.ConfigurableApplicationContext;
import com.akalin.spring.core.io.DefaultResourceLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.在Bean实例化之前,执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4.在Bean实例化之前,执行BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 5.提前实例化单例Bean
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            log.info("registering BeanPostProcessor: {}", beanPostProcessor.getClass().getName());
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
