package com.zumait.springcloud.shadowavatar.mirrorserver;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Nicholas Zhu
 */
@EnableCircuitBreaker
@EnableZuulProxy
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShadowAvatarMirrorServerMarkerConfiguration.class)
public @interface EnableShadowAvatarMirrorServer {
}
