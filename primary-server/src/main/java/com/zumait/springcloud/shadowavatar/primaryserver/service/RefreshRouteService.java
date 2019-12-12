package com.zumait.springcloud.shadowavatar.primaryserver.service;

import com.zumait.springcloud.shadowavatar.primaryserver.ShadowAvatarRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;


public class RefreshRouteService {
    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    ShadowAvatarRouteLocator routeLocator;

    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }

    public List<Route> loadRoutes() {
        return routeLocator.getRoutes();
    }

}
