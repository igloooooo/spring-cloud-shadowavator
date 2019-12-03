package com.zumait.springcloud.shadowavatar.primaryserver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

/**
 * @author Nicholas Zhu
 */
@EnableCircuitBreaker
@EnableZuulProxy
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShadowAvatarPrimaryServerMarkerConfiguration.class)
public @interface EnableShadowAvatarPrimaryServer {
}