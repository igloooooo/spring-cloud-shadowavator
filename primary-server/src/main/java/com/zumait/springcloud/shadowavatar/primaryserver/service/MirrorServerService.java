package com.zumait.springcloud.shadowavatar.primaryserver.service;

import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MirrorServerService {

    private static final Logger logger = LoggerFactory.getLogger(MirrorServerService.class);
    private PathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, MirrorServer> mirrorServerMap = new ConcurrentHashMap<String, MirrorServer>();

    public void registerMirrorServer(MirrorServer mirrorServer) {
        if (mirrorServerMap.containsKey(mirrorServer.getAppName())) {
            logger.warn("existed app: {} will be overwrite. ", mirrorServer.getRouters());
        }
        mirrorServerMap.put(mirrorServer.getAppName(), mirrorServer);
    }

    public void unRegisterMirrorServer(String app) {
        if (mirrorServerMap.containsKey(app)) {
            mirrorServerMap.remove(app);
        } else {
            logger.error("app [{}] does NOT exist!", app);
        }
    }

    public boolean matchURL(String appName, String url) {
        return Optional.ofNullable(mirrorServerMap.get(appName)).map(mirrorServer -> {
            return mirrorServer.getRouters().stream().anyMatch(r -> pathMatcher.match(r.getPath(), url));
        }).orElse(false);
    }

    public Map<String, MirrorServer> getMirrorServerMap() {
        return mirrorServerMap;
    }

    public Optional<MirrorServer> findMirrorServer(String appName) {
        return Optional.ofNullable(mirrorServerMap.get(appName));
    }
}
