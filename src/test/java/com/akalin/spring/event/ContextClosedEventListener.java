package com.akalin.spring.event;

import com.akalin.spring.context.ApplicationListener;
import com.akalin.spring.context.event.ContextClosedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("Received event: {}", event);
    }

}
