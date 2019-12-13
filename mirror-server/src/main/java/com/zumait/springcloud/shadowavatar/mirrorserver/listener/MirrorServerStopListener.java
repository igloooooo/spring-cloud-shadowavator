package com.zumait.springcloud.shadowavatar.mirrorserver.listener;

import com.zumait.springcloud.shadowavatar.mirrorserver.service.MirrorServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;


@Slf4j
public class MirrorServerStopListener implements ApplicationListener<ContextClosedEvent> {

    private MirrorServerService mirrorServerService;

    public MirrorServerStopListener(MirrorServerService mirrorServerService) {
        this.mirrorServerService = mirrorServerService;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextStopEvent) {
        // unregister mirror server
        log.info("Mirror server stop. unregister from primary server");
        mirrorServerService.unregister();
        System.out.println("event: " + contextStopEvent);
    }
}
