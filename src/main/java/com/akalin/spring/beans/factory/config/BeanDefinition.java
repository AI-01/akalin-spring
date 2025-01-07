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

}
