package com.akalin.spring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.PropertyValue;
import com.akalin.spring.beans.PropertyValues;
import com.akalin.spring.beans.factory.*;
import com.akalin.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.BeanPostProcessor;
import com.akalin.spring.beans.factory.config.BeanReference;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 1.实例化bean
            bean = createBeanInstantiation(beanDefinition, beanName, args);
            // 2.依赖注入（属性填充）
            populateBean(beanName, bean, beanDefinition);
            // 3.初始化bean
            bean = initializeBean(bean,beanName,beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        // 注册实现了 DisposableBean 接口的 bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 判断scope为Singleton
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非Singleton类型bean，不执行销毁方法，直接return
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())){
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 创建bean
     */
    private Object createBeanInstantiation(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (null != args && constructor.getParameterTypes().length == args.length) {
                constructorToUse = constructor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 属性填充
     */
    private void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (Objects.isNull(propertyValues)) {
            return;
        }
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
        if (log.isDebugEnabled()) {
            log.debug("Instantiation of bean {} with property values : {}", beanName, propertyValues);
        }
    }


    @Override
    public Object initializeBean(Object existingBean, String beanName, BeanDefinition beanDefinition) throws BeansException {

        // 设置Aware，使得Aware实现类可以获取BeanFactory和BeanClassLoader等信息
        if (existingBean instanceof Aware){
            if (existingBean instanceof BeanFactoryAware){
                ((BeanFactoryAware) existingBean).setBeanFactory(this);
            }
            if (existingBean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) existingBean).setBeanClassLoader(getBeanClassLoader());
            }
            if (existingBean instanceof BeanNameAware){
                ((BeanNameAware) existingBean).setBeanName(beanName);
            }
        }

        // 应用 BeanPostProcessor 的 postProcessBeforeInitialization 方法
        Object wrappedBean = existingBean;
        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);

        // 调用初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean [" + beanName + "] failed", ex);
        }

        // 应用 BeanPostProcessor 的 postProcessAfterInitialization 方法
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception{
        // 1. 实现接口 InitializingBean
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        // 2. 配置信息 init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on class " + beanDefinition.getBeanClass());
            }
            initMethod.invoke(wrappedBean);
        }
    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }


    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

}
