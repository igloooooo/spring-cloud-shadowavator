package com.zumait.springcloud.shadowavatar.primaryserver.router;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.zumait.springcloud.shadowavatar.common.router.MirrorServer;
import com.zumait.springcloud.shadowavatar.primaryserver.service.MirrorServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nicholas Zhu
 */
public class MirrorTraceIDMapperService {
    private Map<String, MirrorServer> mirrorServerMap = new ConcurrentHashMap<String, MirrorServer>();

    @Autowired
    private MirrorServerService mirrorServerService;

    public void addTraceID(String traceId, String appName) {
        mirrorServerService.findMirrorServer(appName).ifPresent(mirrorServer -> {
            mirrorServerMap.put(traceId, mirrorServer);
        });

    }

    public Optional<MirrorServer> getMirrorServer(String traceId) {
        return Optional.ofNullable(mirrorServerMap.get(traceId));
    }

    public void unRegisterMirrorServer(MirrorServer mirrorServer) {
        throw new UnsupportedOperationException("Has not implemented.");
    }
}