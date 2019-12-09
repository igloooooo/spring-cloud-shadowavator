package com.zumait.springcloud.shadowavatar.mirrorserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nicholas Zhu
 */
@RestController
public class ShadowAvatarMirrorController {
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping(value = "/", produces = "text/html")
    public String home() {
        return "<head><title>ShadowAvatar - Mirror Server</title></head><body>\n"
                + "<a href='/ping'>ping</a><br/>\n"
                + "<a href='/actuator/health'>health</a><br/>\n" + "<a href='/hosts/"
                + this.appName + "'>hosts/" + this.appName + "</a><br/>\n" + "</body>";
    }
}
