package com.zumait.springcloud.shadowavatar.primaryserver;

import com.netflix.discovery.EurekaClientConfig;
import com.zumait.springcloud.shadowavatar.primaryserver.filter.SleuthHeaderFilter;
import com.zumait.springcloud.shadowavatar.primaryserver.router.MirrorTraceIDMapperService;
import com.zumait.springcloud.shadowavatar.primaryserver.service.MirrorServerService;
import com.zumait.springcloud.shadowavatar.primaryserver.service.RefreshRouteService;
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
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nicholas Zhu
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ShadowAvatarPrimaryServerMarkerConfiguration.Marker.class)
@EnableConfigurationProperties(ShadowAvatarPrimaryServerProperties.class)
@ConditionalOnProperty(value = "shadowavatar.primaryserver.enabled",
        matchIfMissing = true)
public class ShadowAvatarPrimaryServerAutoConfiguration {

    @Autowired
    ZuulProperties zuulProperties;
    @Autowired
    ServerProperties server;

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
    public ShadowAvatarController shadowAvatarController() {
        return new ShadowAvatarController();
    }

    @Bean
    public SleuthHeaderFilter sleuthHeaderFilter() {
        return new SleuthHeaderFilter();
    }

    @Bean
    public MirrorServerService mirrorServerService(){
        return new MirrorServerService();
    }

    @Bean
    public RefreshRouteService refreshRouteService(){
        return new RefreshRouteService();
    }

    @Bean
    public MirrorTraceIDMapperService mirrorTraceIDMapperService() {
        return new MirrorTraceIDMapperService();
    }

    @Bean
    public ShadowAvatarRouteLocator shadowAvatarRouteLocator (MirrorServerService mirrorServerService) {
        ShadowAvatarRouteLocator routeLocator = new ShadowAvatarRouteLocator(this.server.getServlet().getContextPath(), this.zuulProperties);
        routeLocator.setMirrorServerService(mirrorServerService);
        return routeLocator;
    }

}