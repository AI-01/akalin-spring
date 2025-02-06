package com.akalin.spring.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory,BeanDefinitionRegistry {

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>(); // bean定义缓存



    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String beanName = StrUtil.lowerFirst(ClassUtil.getClassName(type, true));
        T beanInstance = getBean(beanName, type);
        Map<String, T> result = CollectionUtils.newLinkedHashMap(15);
        result.put(beanName,beanInstance);
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public void preInstantiateSingletons() throws org.springframework.beans.BeansException {

    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        Object object = singletonObjects.get(name);
        if (Objects.nonNull(object)) {
            return (T) object;
        }

        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + name + "' is defined");
        }
        return (T) createBean(name, beanDefinition, null);
    }

}
