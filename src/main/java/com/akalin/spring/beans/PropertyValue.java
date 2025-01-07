package com.akalin.spring.beans;

/**
 * bean的属性值
 */
public class PropertyValue {

    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性值
     */
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
