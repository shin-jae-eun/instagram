### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- oauth2-client

```xml
<!-- 시큐리티 태그 라이브러리 -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
</dependency>

<!-- JSP 템플릿 엔진 -->
<dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.43</version>
</dependency>

<!-- JSTL -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
</dependency>
```

### 데이터베이스

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database photogram;
```

### yml 설정

server:
  port: 8000
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true

spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp

  datasource:
    url: jdbc:mysql://localhost:3306/new_insta?serverTimezone=Asia/Seoul
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: new_insta
    password: 1234

  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: update #Create, update, none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true

  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: test
      password: 1234
    oauth2:
      client:
        registration:
          facebook:
            client-id: 387833254211507
            client-secret: 946a76bb3727eaee790f76b5d54c6ca0
            scope:
              - public_profile
              - email

  profiles:
    include: oauth


file:
  path: C:/workspace/springbootwork/upload/

```
 
 
