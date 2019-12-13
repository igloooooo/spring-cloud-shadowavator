package com.zumait.springcloud.shadowavatar.primaryserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zumait.springcloud.shadowavatar.common.ShadowAvatarConstant;
import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import com.zumait.springcloud.shadowavatar.primaryserver.router.MirrorTraceIDMapperService;
import com.zumait.springcloud.shadowavatar.primaryserver.service.MirrorServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;


public class SleuthHeaderFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(SleuthHeaderFilter.class);

    private RouteLocator routeLocator;

    private String dispatcherServletPath;

    private ZuulProperties properties;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    public SleuthHeaderFilter(RouteLocator routeLocator, String dispatcherServletPath, ZuulProperties properties) {
        this.routeLocator = routeLocator;
        this.dispatcherServletPath = dispatcherServletPath;
        this.properties = properties;
    }

    @Autowired
    private MirrorServerService mirrorServerService;
    @Autowired
    private MirrorTraceIDMapperService mirrorTraceIDMapperService;

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        logger.info("Sleuth Header [X-B3-SpanId]: " + requestContext.getRequest().getHeader("X-B3-SpanId"));
        logger.info("Sleuth Header [X-B3-TraceId]: " + requestContext.getRequest().getHeader("X-B3-TraceId"));
        final Optional<String> mirrorServerName = Optional.ofNullable(
                requestContext.getRequest().getHeader(ShadowAvatarConstant.MIRROR_SERVER_HEAD));
        final String url = requestContext.getRequest().getServletPath();
        Optional.ofNullable(requestContext.getRequest().getHeader("X-B3-TraceId"))
                .ifPresent(traceId -> {
                    // register mirror server
                    mirrorServerName.ifPresent(appName -> {
                        mirrorTraceIDMapperService.addTraceID(traceId, appName);
                    });
                    // check if this traceId coming from mirror server and also match the mirror route table
                    ifMatchMirrorServerRoute(traceId, url).ifPresent(mirrorServer -> {
                        // forward to mirror server
                        Object originalRequestPath = requestContext.get(REQUEST_URI_KEY);
                        String modifiedRequestPath = "/" + mirrorServer.getAppName() + url;
                        Route route = this.routeLocator.getMatchingRoute(modifiedRequestPath);
                        if (route != null) {
                            requestContext.getRequest().setAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE, modifiedRequestPath);
                        }
                    });
                });


        return null;
    }

    private Optional<MirrorServer> ifMatchMirrorServerRoute(String traceId, String url) {
        return mirrorTraceIDMapperService.getMirrorServer(traceId).filter(mirrorServer -> {
            return mirrorServerService.matchURL(mirrorServer.getAppName(), url);
        });
    }

    private URL getUrl(String target) {
        try {
            return new URL(target);
        } catch (MalformedURLException var3) {
            throw new IllegalStateException("Target URL is malformed", var3);
        }
    }
}
