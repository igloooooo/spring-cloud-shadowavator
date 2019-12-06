package com.zumait.springcloud.shadowavatar.primaryserver.router;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * @Author Nicholas Zhu
 */
@Service
public class MirrorTraceIDMapperService {
    private Map<String, MirrorServer> mirrorServerMap = new ConcurrentHashMap<String, MirrorServer>();

    public void addTraceID(String traceId, MirrorServer mirrorServer) {
        mirrorServerMap.put(traceId, mirrorServer);
    }

    public Optional<MirrorServer> getMirrorServer(String traceId) {
        return Optional.ofNullable(mirrorServerMap.get(traceId));
    }

    public void unRegisterMirrorServer(MirrorServer mirrorServer) {
        throw new UnsupportedOperationException("Has not implemented.");
    }
}