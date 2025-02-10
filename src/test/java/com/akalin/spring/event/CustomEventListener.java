package com.akalin.spring.event;

import com.akalin.spring.context.ApplicationListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        log.info("Received event: id={}, message={}", event.getId(), event.getMessage());
    }

}
