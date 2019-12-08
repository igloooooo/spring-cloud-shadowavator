package com.zumait.springcloud.shadowavatar.primaryserver;

import com.zumait.springcloud.shadowavatar.primaryserver.router.MirrorServer;
import com.zumait.springcloud.shadowavatar.primaryserver.router.MirrorServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nicholas Zhu
 */
@RestController
public class ShadowAvatarController {
    @Autowired
    private DiscoveryClient discovery;

    @Autowired
    private MirrorServerService routerMapperService;

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/hosts/{appName}")
    public List<ServiceInstance> hosts(@PathVariable("appName") String appName) {
        return hosts2(appName);
    }

    @RequestMapping("/hosts")
    public List<ServiceInstance> hosts2(@RequestParam("appName") String appName) {
        List<ServiceInstance> instances = this.discovery.getInstances(appName);
        return instances;
    }

    @RequestMapping(value = "/", produces = "text/html")
    public String home() {
        return "<head><title>ShadowAvatar - Primary Server</title></head><body>\n"
                + "<a href='/ping'>ping</a><br/>\n"
                + "<a href='/actuator/health'>health</a><br/>\n" + "<a href='/hosts/"
                + this.appName + "'>hosts/" + this.appName + "</a><br/>\n" + "</body>";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerMirror(@RequestBody MirrorServer mirrorServer) {
        routerMapperService.registerMirrorServer(mirrorServer);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register/{appName}", method = RequestMethod.DELETE)
    public ResponseEntity registerMirror(@PathVariable String appName) {
        routerMapperService.unRegisterMirrorServer(appName);
        return ResponseEntity.ok().build();
    }

}
