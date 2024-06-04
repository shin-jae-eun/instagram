# 📝Instagram clone coding
##  **💡Spring Boot를 활용하여 인스타그램 클론코딩**

- **프로젝트 기간 : 2024.03~2024.08**
- [프로젝트를 진행하며 공부했던 기능들을 참고한 기술블로그 링크](https://blog.naver.com/fwangjuwon/222744591409)
    
<br/>

## 💡 프로젝트 목표
- 실제 인스타그램 서비스를 따라서 만들어보며 스프링부트에 대한 이해도 향상
- jsp 템플릿엔진 사용해보기  
- spring security 기능 사용해보기
- AOP에 대한 이해, 처리해보기 
- 테이블간 연관관계에 대한 이해도 향상
- springboot jpa 활용 능력 향상 

<br/>

 ## 💡 사용 기술

<img src="https://img.shields.io/badge/-Java-007396"/>  <img src="https://img.shields.io/badge/-Spring-6DB33F"/>  <img src="https://img.shields.io/badge/-Apach%20Tomcat-F8DC75"/> <img src="https://img.shields.io/badge/-MariaDB-071D49"/> 
<img src="https://img.shields.io/badge/-HTML5-E34F26"/> <img src="https://img.shields.io/badge/-CSS-1572B6"/> <img src="https://img.shields.io/badge/-JavaScript-F7DF1E"/> <img src="https://img.shields.io/badge/-JQuery-0769AD"/> 
<img src="https://img.shields.io/badge/-Github-181717"/> <img src="https://img.shields.io/badge/-Git-F05032"/> <img src="https://img.shields.io/badge/-BootStrap-7952B3"/> 

- **개발 언어**: Java 11, HTML 5, CSS, JavaScript
- **DataBase**: MariaDB 10.6

- **Library**
- ***Front***
    - Bootstrap 5.1.3, jQuery 3.5.1, Summernote, jsp
- ***Back***
    - Spring Web, Spring Boot Devtools, Spring Security, Lombok, MariaDB Driver, Spring Data JPA, OAuth
- **개발 환경** : VS Code, SpringBoot 2.5.12, Maven, Lombok, JPA
<br/>
  
## 💡주요 구현 기능
- 사용자 구독 기능
- 소셜 로그인 기능(facebook OAuth)
- 게시글 좋아요 기능
- 회원정보 수정 및 프로필 사진 변경 기능

| 사용자 구독 | 소셜 로그인 (OAuth) |
|------|------|
|![subscribe](https://user-images.githubusercontent.com/97711663/176613592-96501a76-ae5b-4ac5-adc3-afe9c4a17685.gif)|![oauth](https://user-images.githubusercontent.com/97711663/176613589-95f1686e-f982-46bb-a64b-0194293483c8.gif)|
| 게시글 좋아요 | 회원정보 수정 및 프로필사진 변경 |
|![like](https://user-images.githubusercontent.com/97711663/176613584-3e4febf3-a99d-4c46-b1c2-4febf139d9a8.gif)|![userupdate](https://user-images.githubusercontent.com/97711663/176613596-fe6eb9a5-1024-4105-b724-acaae016e048.gif)|


<br/>

<br/>

## 💡ERD
<img width="601" alt="Screenshot_14" src="https://user-images.githubusercontent.com/97711663/176632750-acefe3d8-f45d-445b-b8e9-b1e558044418.png">


<br/>

## 💡 프로젝트 리뷰 및 개선방향
- Spring security로 인증과 보안 기능을 강화시킬 수 있어서 좋았다.
- AOP처리를 하면서 중복 코드를 줄일 수 있다는 점이 좋았고, 앞으로 이런 기능을 더 많이 활용하여 clean code를 작성할 수 있는 개발자가 돼야겠다. 
- OAuth 라이브러리를 사용해서 쉽게 회원가입을 할 수 있는 기능이 좋았고, 다른 sns 로그인 기능도 추가해 볼 생각이다. 
- 다음에는 AWS를 배워서 배포를 해봐야겠다. 
- 사용자의 관점과 편의를 더 생각하고 고민하여 개발해야겠다. 
