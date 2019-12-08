package com.zumait.springcloud.shadowavatar.common.router;

import java.util.LinkedList;
import java.util.List;

public class MirrorServer {
    private String url;
    private String appName;
    private List<MirrorRoute> routers = new LinkedList<>();

    public MirrorServer () {

    }

    public MirrorServer(String url, String appName, List<MirrorRoute> routers) {
        this.url = url;
        this.appName = appName;
        this.routers = routers;
    }

    public MirrorServer(String url, String appName) {
        this.url = url;
        this.appName = appName;
    }

    public void removeRoute(MirrorRoute route) {
        routers.removeIf(r -> r.getId() == route.getId());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<MirrorRoute> getRouters() {
        return routers;
    }

    public void setRouters(List<MirrorRoute> routers) {
        this.routers = routers;
    }
}
