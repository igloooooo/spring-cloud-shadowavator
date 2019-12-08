package com.zumait.springcloud.shadowavatar.mirrorserver;

import com.zumait.springcloud.shadowavatar.mirrorserver.filter.MirrorServerFilter;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nicholas Zhu
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ShadowAvatarMirrorServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(ShadowAvatarMirrorServerProperties.class)
@ConditionalOnProperty(value = "spring.cloud.netflix.shadowavatar.mirrorserver.enabled",
        matchIfMissing = true)
public class ShadowAvatarMirrorServerAutoConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public HasFeatures Feature() {
        return HasFeatures.namedFeature("ShadowAvatar Mirror Server",
                ShadowAvatarMirrorServerAutoConfiguration.class);
    }

    @Bean
    @ConditionalOnMissingClass("org.apache.http.client.HttpClient")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    @ConditionalOnClass(HttpClient.class)
    public RestTemplate sslRestTemplate(ShadowAvatarMirrorServerProperties properties) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        if (properties.isAcceptAllSslCertificates()) {
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            builder = builder.requestFactory(() -> requestFactory);
        }
        return builder.build();
    }

    @Bean
    public ShadowAvatarMirrorController sidecarController() {
        return new ShadowAvatarMirrorController();
    }

    @Bean
    public MirrorServerFilter mirrorServerFilter(){
        return new MirrorServerFilter(appName);
    }
}
