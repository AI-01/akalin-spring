package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.akalin.spring.beans.factory.config.BeanPostProcessor;
import com.akalin.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.akalin.spring.context.ApplicationEvent;
import com.akalin.spring.context.ApplicationListener;
import com.akalin.spring.context.ConfigurableApplicationContext;
import com.akalin.spring.context.event.ApplicationEventMulticaster;
import com.akalin.spring.context.event.ContextClosedEvent;
import com.akalin.spring.context.event.ContextRefreshedEvent;
import com.akalin.spring.context.event.SimpleApplicationEventMulticaster;
import com.akalin.spring.core.io.DefaultResourceLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.在BeanFactory中添加BeanPostProcessor，让实现ApplicationContextAware的类可以感知到ApplicationContext2
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4.在Bean实例化之前,执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5.在Bean实例化之前,注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 6.提前实例化单例Bean
        beanFactory.preInstantiateSingletons();

        // 7.初始化事件发布者
        initApplicationEventMulticaster();

        // 8.注册事件监听器
        registerListeners();

        // 9.发布容器刷新完成事件
        finishRefresh();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            log.info("invoking BeanFactoryPostProcessor: {}", beanFactoryPostProcessor.getClass().getName());
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

    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    private void registerListeners(){
        getBeansOfType(ApplicationListener.class).values().forEach(applicationEventMulticaster::addApplicationListener);
    }

    private void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 1.发布容器关闭完成事件
        publishEvent(new ContextClosedEvent(this));

        // 2.销毁单例Bean
        getBeanFactory().destroySingletons();
    }
}
