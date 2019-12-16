# spring-cloud-shadowavator
ShadowAvatar aims to build local micro service development environment. It will support merge your local microservice into test environment, such as QA, without impact it.

There are two major modules: Primary Server and Mirror Server.

### Primary Server
Primary server is an API gateway to governor test environment. All microservices will connect to each other via it.
 
### Mirror Server
Mirror server is an API gateway for local developer environment. All local developing microservice can be accessed via it.

### Context
![image](https://github.com/igloooooo/spring-cloud-shadowavator/blob/master/doc/image/ShadowAvatar-context.png)

### How to run
Include dependency

To enable primary server
```xml
    <dependency>
        <groupId>com.zumait.springcloud.shadowavatar</groupId>
        <artifactId>primary-server</artifactId>
        <version>1.0.0</version>
    </dependency>
```
```java
@SpringBootApplication
@EnableShadowAvatarPrimaryServer
public class ExamplePrimaryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplePrimaryServerApplication.class, args);
	}

}
```

To enable mirror server
```xml
    <dependency>
        <groupId>com.zumait.springcloud.shadowavatar</groupId>
        <artifactId>mirror-server</artifactId>
        <version>1.0.0</version>
    </dependency>
```
```java
@SpringBootApplication
@EnableShadowAvatarMirrorServer
public class ExampleMirrorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleMirrorServerApplication.class, args);
    }

}
```

###TODO
1. Integration with Eureka Server
2. Support Feign client
