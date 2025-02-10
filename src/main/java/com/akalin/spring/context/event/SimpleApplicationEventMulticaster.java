package com.akalin.spring.context.event;

import com.akalin.spring.beans.factory.BeanFactory;
import com.akalin.spring.context.ApplicationEvent;
import com.akalin.spring.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<?> listener : getApplicationListeners(event)) {
            invokeListener(listener, event);
        }
    }

    protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
        doInvokeListener(listener, event);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
        listener.onApplicationEvent(event);
    }

}
