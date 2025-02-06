package com.akalin.spring.beans.factory.config;

import com.akalin.spring.beans.PropertyValues;

public class BeanDefinition {

    /**
     * bean类型
     */
    private Class beanClass;
    /**
     * bean属性集
     */
    private PropertyValues propertyValues;
    /**
     * 初始化方法名称
     */
    private String initMethodName;
    /**
     * 销毁方法名称
     */
    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this(beanClass, new PropertyValues());
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
