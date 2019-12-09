package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import com.zumait.springcloud.shadowavatar.mirrorserver.service.MirrorServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;


@Slf4j
public class MirrorServerStartUpListener implements ApplicationListener<ContextStartedEvent> {
    @Autowired
    private MirrorServerService mirrorServerService;

    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        log.info("Mirror server startup. register to primary server");
        mirrorServerService.register();

    }
}
