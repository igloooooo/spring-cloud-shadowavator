package com.zumait.springcloud.shadowavatar.mirrorserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class MirrorServerService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("mirrorserver.primaryserver")
    private String primaryServerURL;

    public void register() {

//        restTemplate.postForObject(primaryServerURL, )
    }

}
