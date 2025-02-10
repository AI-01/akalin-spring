package com.akalin.spring.context.event;

import com.akalin.spring.context.ApplicationEvent;
import com.akalin.spring.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    /**
     * 添加监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     */
    void multicastEvent(ApplicationEvent event);

}
