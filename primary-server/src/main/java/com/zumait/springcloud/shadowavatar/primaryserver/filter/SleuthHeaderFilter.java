package com.zumait.springcloud.shadowavatar.primaryserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import com.zumait.springcloud.shadowavatar.primaryserver.service.MirrorServerService;
import com.zumait.springcloud.shadowavatar.primaryserver.router.MirrorTraceIDMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.util.Optional;

@Component
public class SleuthHeaderFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(SleuthHeaderFilter.class);

    private RouteLocator routeLocator;

    private String dispatcherServletPath;

    private ZuulProperties properties;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    private MirrorServerService mirrorServerService;
    @Autowired
    private MirrorTraceIDMapperService mirrorTraceIDMapperService;

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 6;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        logger.info("Sleuth Header [X-B3-SpanId]: " + requestContext.getRequest().getHeader("X-B3-SpanId"));
        logger.info("Sleuth Header [X-B3-TraceId]: " + requestContext.getRequest().getHeader("X-B3-TraceId"));
        Optional.ofNullable(requestContext.getRequest().getHeader("X-B3-TraceId"))
        .ifPresent(traceId -> {
            // check if this traceId coming from mirror server and also match the mirror route table
            ifMatchMirrorServerRoute(traceId).ifPresent(mirrorServer -> {
                // forward to mirror server
                String newRequestURI = "/" + mirrorServer.getAppName() + "/"
                        + urlPathHelper.getPathWithinApplication(requestContext.getRequest());
                requestContext.set("requestURI", newRequestURI);
            });
        });


        return null;
    }

    private Optional<MirrorServer> ifMatchMirrorServerRoute(String traceId){
        return Optional.empty();
    }
}
