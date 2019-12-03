package com.zumait.springcloud.shadowavatar.primaryserver;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nicholas Zhu
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ShadowAvatarPrimaryServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(ShadowAvatarPrimaryServerProperties.class)
@AutoConfigureBefore(EurekaClientAutoConfiguration.class)
@ConditionalOnProperty(value = "spring.cloud.netflix.sidecar.enabled",
        matchIfMissing = true)
public class ShadowAvatarPrimaryServerAutoConfiguration {
}