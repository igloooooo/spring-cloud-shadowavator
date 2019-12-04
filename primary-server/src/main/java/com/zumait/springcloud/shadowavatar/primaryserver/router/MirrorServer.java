package com.zumait.springcloud.shadowavatar.primaryserver.router;

import java.util.LinkedList;
import java.util.List;

public class MirrorServer {
    private String url;
    private String appName;
    private List<String> routers = new LinkedList<>();

    public MirrorServer () {

    }

    public MirrorServer(String url, String appName, List<String> routers) {
        this.url = url;
        this.appName = appName;
        this.routers = routers;
    }

    public MirrorServer(String url, String appName) {
        this.url = url;
        this.appName = appName;
    }

    public void removeRoute(String route) {
        routers.removeIf(r -> r.equalsIgnoreCase(route));
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

    public List<String> getRouters() {
        return routers;
    }

    public void setRouters(List<String> routers) {
        this.routers = routers;
    }
}
