package com.zumait.springcloud.shadowavatar.example.devservice2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@SpanName("qa-service-1")
@Slf4j
public class DevService2Application {

	public static void main(String[] args) {
		SpringApplication.run(DevService2Application.class, args);
	}

	@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
	public String trace() {
		log.info("this is call dev trace-2");
		return "this is dev trace-2";
	}

}
