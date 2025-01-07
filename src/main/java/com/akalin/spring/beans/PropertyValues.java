package com.akalin.spring.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    /**
     * 添加属性值
     * @param pv 属性值
     */
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValues.add(pv);
    }

    /**
     * 获取属性值数组
     * @return 属性值数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名获取属性值
     * @param propertyName 属性名
     * @return 属性值
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValues) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

}
