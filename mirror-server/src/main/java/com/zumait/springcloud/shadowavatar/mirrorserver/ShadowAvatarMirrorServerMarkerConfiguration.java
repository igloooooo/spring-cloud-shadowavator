package com.zumait.springcloud.shadowavatar.mirrorserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nicholas Zhu
 */
@Configuration(proxyBeanMethods = false)
public class ShadowAvatarMirrorServerMarkerConfiguration {
    @Bean
    public Marker saPrimaryServerMarkerBean() {
        return new Marker();
    }

    class Marker {

    }
}
