package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.FactoryBean;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.BeanPostProcessor;
import com.akalin.spring.beans.factory.config.ConfigurableBeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    /**
     * bean类加载器
     */
    private ClassLoader beanClassLoader = ClassLoader.getSystemClassLoader();

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name,null);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null){
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (beanInstance instanceof FactoryBean){
            Object object = getCachedObjectForFactoryBean(beanName);
            if (object == null){
                FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
                object = getObjectFromFactoryBean(factoryBean,beanName);
            }
            return object;
        }
        return beanInstance;
    }

    /**
     * 获取bean定义
     * @param beanName bean名称
     * @return bean定义
     * @throws BeansException bean异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建bean实例
     * @param beanName bean名称
     * @param beanDefinition bean定义
     * @param args bean构造函数参数
     * @return bean实例
     * @throws BeansException bean异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        synchronized (this.beanPostProcessors) {
            // Remove from old position, if any
            this.beanPostProcessors.remove(beanPostProcessor);
            // Add to end of list
            this.beanPostProcessors.add(beanPostProcessor);
        }
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

}
