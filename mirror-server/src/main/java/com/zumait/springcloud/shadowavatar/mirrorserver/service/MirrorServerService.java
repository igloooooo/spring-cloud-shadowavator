package com.zumait.springcloud.shadowavatar.mirrorserver.service;

import com.zumait.springcloud.shadowavatar.common.router.MirrorRoute;
import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MirrorServerService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("mirrorserver.primaryserver")
    private String primaryServerURL;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.port}")
    private String port;

    @Autowired
    private SimpleRouteLocator simpleRouteLocator;

    public void register() {
        MirrorServer mirrorServer = new MirrorServer();
        mirrorServer.setAppName(appName);
        mirrorServer.setUrl("http://localhost:" + port);
        mirrorServer.setRouters(getRoutes().stream().map(r -> {
            MirrorRoute mr = new MirrorRoute();
            org.springframework.beans.BeanUtils.copyProperties(r,mr);
            mr.setAppName(appName);
            return mr;
        }).collect(Collectors.toList()));
        restTemplate.postForObject(primaryServerURL, mirrorServer, ResponseEntity.class, mirrorServer);
    }

    public void unregister() {
        restTemplate.delete(primaryServerURL+"/unregister/" + appName, ResponseEntity.class);
    }

    public List<ZuulProperties.ZuulRoute> getRoutes(){
        return simpleRouteLocator.getRoutes().stream()
                .map(r -> {
                    ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
                    org.springframework.beans.BeanUtils.copyProperties(r,zuulRoute);
                    return zuulRoute;
                }).collect(Collectors.toList());
    }
}
