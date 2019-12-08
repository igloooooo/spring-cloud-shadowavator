package com.zumait.springcloud.shadowavatar.example.exampleprimaryserver;

import com.zumait.springcloud.shadowavatar.primaryserver.EnableShadowAvatarPrimaryServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableShadowAvatarPrimaryServer
public class ExamplePrimaryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplePrimaryServerApplication.class, args);
	}

}
