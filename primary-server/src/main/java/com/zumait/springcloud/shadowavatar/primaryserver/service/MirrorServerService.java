package com.zumait.springcloud.shadowavatar.primaryserver.service;

import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MirrorServerService {

    private static final Logger logger = LoggerFactory.getLogger(MirrorServerService.class);
    private PathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, MirrorServer> mirrorServerMap = new ConcurrentHashMap<String, MirrorServer>();

    public void registerMirrorServer(MirrorServer mirrorServer) {
        if (mirrorServerMap.containsKey(mirrorServer.getAppName())) {
            logger.error("can NOT add existed app: " + mirrorServer.getRouters());
        } else {

            mirrorServerMap.put(mirrorServer.getAppName(), mirrorServer);
        }
    }

    public void unRegisterMirrorServer(String app) {
        if (mirrorServerMap.containsKey(app)) {
            mirrorServerMap.remove(app);
        } else {
            logger.error("app [{}] does NOT exist!", app);
        }
    }

//    public Optional<MirrorServer> matchURL(String url) {
//        return mirrorServerMap.entrySet().stream().filter(entry ->
//            entry.getValue().getRouters().stream().anyMatch(patten -> pathMatcher.match(patten, url))
//        ).map(Map.Entry::getValue).findFirst();
//    }

    public Map<String, MirrorServer> getMirrorServerMap() {
        return mirrorServerMap;
    }
}
