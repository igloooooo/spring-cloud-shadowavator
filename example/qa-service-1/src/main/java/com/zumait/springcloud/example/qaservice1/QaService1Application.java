package com.zumait.springcloud.example.qaservice1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@SpanName("qa-service-1")
@Slf4j
public class QaService1Application {

	public static void main(String[] args) {
		SpringApplication.run(QaService1Application.class, args);
	}

	@Bean
	RestTemplate restTemplate () {
		return new RestTemplate();
	}

	@RequestMapping(value = "/trace-1", method = RequestMethod.GET)
	public String trace() {
		log.info("this is call trace-1");
		return restTemplate().getForEntity("http://localhost:8730/service2/trace-2", String.class).getBody();
	}
}
