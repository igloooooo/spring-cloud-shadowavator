package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MirrorServerStartUpListener implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        log.info("Mirror server startup. register to primary server");
    }
}
