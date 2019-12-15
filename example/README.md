# Example for ShadowAvatar

### Context

### How to run
1. start up eureka server.
2. start up primary server firstly.
3. start up qa-server-1 and qa-service-2
4. start up mirror server.
5. start up dev-service-2.

### How to test
```xml
GET http://localhost:8830/service1/trace-1
```
will access qa-service-2 and return will be
```xml
this is trace-2
```
But if you send request from dev environment via
```xml
GET http://localhost:8840/service1/trace-1
```
will access dev-service-2 and return will be
```xml
this is dev trace-2
```