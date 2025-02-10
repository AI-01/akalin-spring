package com.akalin.spring.context.event;

import com.akalin.spring.context.ApplicationContext;
import com.akalin.spring.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }

}
