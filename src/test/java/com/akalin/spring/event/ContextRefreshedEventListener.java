package com.akalin.spring.event;

import com.akalin.spring.context.ApplicationListener;
import com.akalin.spring.context.event.ContextRefreshedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Received event: {}", event);
    }

}
