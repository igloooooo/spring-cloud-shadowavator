package com.zumait.springcloud.shadowavatar.primaryserver;

import com.netflix.discovery.EurekaClientConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nicholas Zhu
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ShadowAvatarPrimaryServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(ShadowAvatarPrimaryServerProperties.class)
@ConditionalOnProperty(value = "spring.cloud.netflix.shadowavatar.enabled",
        matchIfMissing = true)
public class ShadowAvatarPrimaryServerAutoConfiguration {
    @Bean
    public HasFeatures Feature() {
        return HasFeatures.namedFeature("ShadowAvatar Primary Server",
                ShadowAvatarPrimaryServerAutoConfiguration.class);
    }

    @Bean
    @ConditionalOnMissingClass("org.apache.http.client.HttpClient")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    @ConditionalOnClass(HttpClient.class)
    public RestTemplate sslRestTemplate(ShadowAvatarPrimaryServerProperties properties) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        if (properties.getAcceptAllSslCertificates()) {
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            builder = builder.requestFactory(() -> requestFactory);
        }
        return builder.build();
    }

    @Bean
    public ShadowAvatarController sidecarController() {
        return new ShadowAvatarController();
    }

}