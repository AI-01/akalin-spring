package com.akalin.spring.context;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    /**
     * 构造事件原型
     * @param source 目标观察的事件源
     */
    public ApplicationEvent(Object source) {
        super(source);
    }

}
