package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import com.zumait.springcloud.shadowavatar.mirrorserver.service.MirrorServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;


@Slf4j
@Configuration
public class MirrorServerStartUpListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private MirrorServerService mirrorServerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Mirror server startup. register to primary server");
        mirrorServerService.register();
        System.out.println("event: " + applicationReadyEvent);
    }
}
