# Study-SpringBoot

> Specification

- spring-boot-starter-web
- org.apache.httpcomponents.httpclient - RestTemplate
- springfox-swagger2, springfox-swagger-ui 

## Assignment Point

- change port number in embeded tomcat
- test naver open api that search book 
- apply @RestController and Query parameter
- don't use @Autowired and @Data annotation

### Structures

```text
app/
├─ gradle
├─ src/
│  └─ main
│     └─ java
│        └─ common
│        └─ config
│        └─ controller
│        └─ model
│        └─ service
│     └─ resources
│  └─ test
├─ .gitignore
├─ build.gradle
├─ gradlew
├─ gradlew.bat
├─ README.md
├─ settings.gradle
```

### Settings

> `application.properties`

- server.port=8081