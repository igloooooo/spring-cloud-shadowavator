package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import com.zumait.springcloud.shadowavatar.mirrorserver.service.MirrorServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;


@Slf4j
@Configuration
public class MirrorServerStartUpListener implements ApplicationListener<ApplicationEvent> {
    @Autowired
    private MirrorServerService mirrorServerService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationReadyEvent) {
            log.info("Mirror server startup. register to primary server");
            mirrorServerService.register();
        } else if (applicationEvent instanceof ContextStoppedEvent) {
            log.info("Mirror server stopped. unregister from primary server");
            mirrorServerService.unregister();
        }
    }
}
