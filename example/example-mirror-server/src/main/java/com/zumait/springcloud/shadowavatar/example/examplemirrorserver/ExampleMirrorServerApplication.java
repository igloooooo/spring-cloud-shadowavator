package com.zumait.springcloud.shadowavatar.example.examplemirrorserver;

import com.zumait.springcloud.shadowavatar.mirrorserver.EnableShadowAvatarMirrorServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableShadowAvatarMirrorServer
public class ExampleMirrorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleMirrorServerApplication.class, args);
    }

}
