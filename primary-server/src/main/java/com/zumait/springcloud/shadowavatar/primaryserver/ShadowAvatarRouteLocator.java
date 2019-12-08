package com.zumait.springcloud.shadowavatar.primaryserver;

import com.zumait.springcloud.shadowavatar.primaryserver.service.MirrorServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShadowAvatarRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    public final static Logger logger = LoggerFactory.getLogger(ShadowAvatarRouteLocator.class);

    private MirrorServerService mirrorServerService;

    private ZuulProperties properties;

    public void setJdbcTemplate(MirrorServerService mirrorServerService){
        this.mirrorServerService = mirrorServerService;
    }

    public ShadowAvatarRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}",servletPath);
    }


    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
        //load from mirror servers
        routesMap.putAll(locateRoutesFromMirrorServer());
        //load from application.properties
        routesMap.putAll(super.locateRoutes());
        //优化一下配置
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulRoute> locateRoutesFromMirrorServer(){
        return mirrorServerService.getMirrorServerMap().values().stream().flatMap(mirrorServer -> {
            return mirrorServer.getRouters().stream()
                    .filter(mirrorRoute ->
                            (!StringUtils.isEmpty(mirrorRoute.getPath()) && (!StringUtils.isEmpty(mirrorRoute.getUrl()))))
                    .map(route -> {
                        ZuulRoute zuulRoute = new ZuulRoute();
                        org.springframework.beans.BeanUtils.copyProperties(route,zuulRoute);
                        zuulRoute.setPath("/" +route.getAppName() + "/" + zuulRoute.getPath());
                        return zuulRoute;
                    });
        }).collect(Collectors.toMap(r -> r.getPath(),r -> r));
    }
}
