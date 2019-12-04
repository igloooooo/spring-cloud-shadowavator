package com.zumait.springcloud.shadowavatar.primaryserver.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RouterMapperService {

    private static final Logger logger = LoggerFactory.getLogger(RouterMapperService.class);
    private PathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, MirrorServer> mirrorServerMap = new ConcurrentHashMap<String, MirrorServer>();

    public void addRoute(String app, String route) {
        if (mirrorServerMap.containsKey(app)) {
            mirrorServerMap.get(app).getRouters().add(route);
        } else {
            logger.error("can NOT add route into app: " + app);
        }
    }

    public void registerRoute(MirrorServer mirrorServer) {
        if (mirrorServerMap.containsKey(mirrorServer.getAppName())) {
            logger.error("can NOT add existed app: " + mirrorServer.getRouters());
        } else {

            mirrorServerMap.put(mirrorServer.getAppName(), mirrorServer);
        }
    }

    public void unRegisterRoute(String app, String route) {
        if (mirrorServerMap.containsKey(app)) {
            mirrorServerMap.get(app).removeRoute(route);
        } else {
            logger.error("app [{}] does NOT exist!", app);
        }
    }

    public Optional<MirrorServer> matchURL(String url) {
        return mirrorServerMap.entrySet().stream().filter(entry ->
            entry.getValue().getRouters().stream().anyMatch(patten -> pathMatcher.match(patten, url))
        ).map(Map.Entry::getValue).findFirst();
    }
}
