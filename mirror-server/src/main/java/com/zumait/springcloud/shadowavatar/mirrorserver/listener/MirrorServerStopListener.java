package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MirrorServerStopListener implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent contextStopEvent) {
        // unregister mirror server
        log.info("Mirror server stop. unregister from primary server");
    }
}
