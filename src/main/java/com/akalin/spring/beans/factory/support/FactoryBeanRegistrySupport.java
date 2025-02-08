package com.akalin.spring.beans.factory.support;

import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBean注册服务
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    /**
     * 缓存FactoryBean创建的单例对象，key：FactoryBean对象的beanName，value：FactoryBean创建的单例对象
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) throws BeansException {
        if (factory.isSingleton()){
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null){
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, object);
            }
            return object;
        }

        return doGetObjectFromFactoryBean(factory, beanName);
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) throws BeansException {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
