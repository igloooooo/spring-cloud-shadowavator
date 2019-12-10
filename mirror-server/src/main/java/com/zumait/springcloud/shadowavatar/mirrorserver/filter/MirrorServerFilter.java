package com.zumait.springcloud.shadowavatar.mirrorserver.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zumait.springcloud.shadowavatar.common.ShadowAvatarConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author Nicholas Zhu
 */
public class MirrorServerFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(MirrorServerFilter.class);
    private String appName;

    public MirrorServerFilter(String appName) {
        this.appName = appName;
    }
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.getThrowable() == null
                && (context.getZuulRequestHeaders().isEmpty() || (!context.getZuulRequestHeaders().isEmpty()
                && !context.getZuulRequestHeaders().containsKey(ShadowAvatarConstant.MIRROR_SERVER_HEAD)));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader(ShadowAvatarConstant.MIRROR_SERVER_HEAD, this.appName);
        return null;
    }
}
