# Sub PJT 02 ( 07/20 ~ 07/31 ) - 굿닥터



## 🎈 프로젝트 이름

굿닥터



## 📖 프로젝트 소개

누구나 한번 쯤 병원을 찾고자 할때 어떠한 병원이 좋은 병원인지 단순하게 블로그등에 있는 리뷰만을 믿고

찾아갔다가 낭패를 본 경험이 있을것이다. 이러한 피해자들이 생기지 않도록 실제 사용자들이 병원을 방문하고 

신뢰성 있는 리뷰 작성을 통해 사용자간의 정보공유가 가능한 SNS를 만들고자 한다. 이러한 신뢰성의 근거로

우리는 QR코드를 사용하고자 한다. 기존에 있던 병원에 대한 리뷰 시스템이 없던것은 아니다 하지만 단순하게

병원진료 후의 영수증 사진을 업로드하고 눈으로 확인하는 정도의 방식이기에 리뷰에 대한 신뢰성이 100%라고는

말할 수 없다. 하지만 QR코드를 진료 후 스캔을 통해 해당하는 회원에 대해서만 리뷰를 작성하도록 만들어 준다면

QR코드를 받은 회원이 자신의 계정을 통해서만 리뷰를 작성할 수 있기 때문에 좀 더 확실한 신뢰성을 부여할 수 있다.

이외에도 사용자에게 병원에 대한 정보 및 위치를 얻기 편하도록 여러가지 기능들이 추가되있다.



## 💻 사용언어, 기술스택

![Generic badge](https://img.shields.io/badge/platform-Web-brightgreen.svg) ![Generic badge](https://img.shields.io/badge/library-vue-blue.svg) ![Generic badge](https://img.shields.io/badge/framework-spring-green.svg)
![Generic badge](https://img.shields.io/badge/database-MySQL-yellow.svg) ![Generic badge](https://img.shields.io/badge/server-AWS-9cf.svg) ![Generic badge](https://img.shields.io/badge/language-Java, JavaScript -important.svg)



## 📜 기획

### 와이어프레임
[이동하기](https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a307/blob/develop/doc/_%EC%84%9C%EC%9A%B8A307_%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84.pdf)

### ERD (추후 수정 요망)
[이동하기](https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a307/blob/develop/doc/_%EC%84%9C%EC%9A%B8_A307_Database_ERD.png)

---



## 🎮 테스트하기

## IDE
```
VSCode
STS
```

### 저장소 클론하기

```
깃 저장소를 로컬에 복사합니다.
git clone https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a307.git
```


### Run - Frontend
```
npm i
npm run serve -- --port 3000
```

### Run - Backend
```
git clone 이후, STS에서 File -> Open Projects from File System -> skeleton-back을 import하고 Finish
skeleton-back 패키지 우클릭 -> run as -> Spring Boot App
```



## 📕 서비스 설명

```
## 폴더 구조 - Frontend
📦src
 ┣ 📂api
 ┃ ┗ 📜UserApi.js
 ┣ 📂assets
 ┃ ┣ 📂css
 ┃ ┃ ┣ 📜common.scss
 ┃ ┃ ┣ 📜feed.scss
 ┃ ┃ ┣ 📜feedModal.scss
 ┃ ┃ ┣ 📜main.css
 ┃ ┃ ┣ 📜style.scss
 ┃ ┃ ┗ 📜user.scss
 ┃ ┣ 📂images
 ┃ ┃ ┣ 📂hospital
 ┃ ┃ ┃ ┣ 📜hospital1.png
 ┃ ┃ ┃ ┣ 📜hospital10.png
 ┃ ┃ ┃ ┣ 📜hospital11.png
 ┃ ┃ ┃ ┣ 📜hospital12.png
 ┃ ┃ ┃ ┣ 📜hospital13.png
 ┃ ┃ ┃ ┣ 📜hospital14.png
 ┃ ┃ ┃ ┣ 📜hospital15.png
 ┃ ┃ ┃ ┣ 📜hospital16.png
 ┃ ┃ ┃ ┣ 📜hospital17.png
 ┃ ┃ ┃ ┣ 📜hospital18.png
 ┃ ┃ ┃ ┣ 📜hospital2.png
 ┃ ┃ ┃ ┣ 📜hospital3.png
 ┃ ┃ ┃ ┣ 📜hospital4.png
 ┃ ┃ ┃ ┣ 📜hospital5.png
 ┃ ┃ ┃ ┣ 📜hospital6.png
 ┃ ┃ ┃ ┣ 📜hospital7.png
 ┃ ┃ ┃ ┣ 📜hospital8.png
 ┃ ┃ ┃ ┗ 📜hospital9.png
 ┃ ┃ ┣ 📜check_b.png
 ┃ ┃ ┣ 📜check_w.png
 ┃ ┃ ┣ 📜feed-sample.jpg
 ┃ ┃ ┣ 📜img-placeholder.png
 ┃ ┃ ┣ 📜logo.png
 ┃ ┃ ┣ 📜profile_default.png
 ┃ ┃ ┣ 📜select-arrow.png
 ┃ ┃ ┗ 📜welcome.png
 ┃ ┗ 📜logo.png
 ┣ 📂components
 ┃ ┣ 📂accounts
 ┃ ┃ ┗ 📂snsLogin
 ┃ ┃ ┃ ┣ 📜Google.vue
 ┃ ┃ ┃ ┗ 📜Kakao.vue
 ┃ ┣ 📂css
 ┃ ┃ ┗ 📂feed
 ┃ ┃ ┃ ┗ 📜feed-item.scss
 ┃ ┣ 📂feed
 ┃ ┃ ┣ 📜FeedItem.vue
 ┃ ┃ ┗ 📜FeedModal.vue
 ┃ ┣ 📜errorPage.vue
 ┃ ┣ 📜NavigationBar.vue
 ┃ ┗ 📜PageNotFound.vue
 ┣ 📂router
 ┃ ┗ 📜index.js
 ┣ 📂util
 ┃ ┗ 📜http-common.js
 ┣ 📂views
 ┃ ┣ 📂accounts
 ┃ ┃ ┣ 📜changePassword.vue
 ┃ ┃ ┣ 📜findPassword.vue
 ┃ ┃ ┣ 📜Login.vue
 ┃ ┃ ┗ 📜Signup.vue
 ┃ ┗ 📂feed
 ┃ ┃ ┗ 📜IndexFeed.vue
 ┣ 📂vuex
 ┃ ┣ 📜actions.js
 ┃ ┣ 📜getters.js
 ┃ ┣ 📜mutations.js
 ┃ ┗ 📜store.js
 ┣ 📜App.vue
 ┗ 📜main.js

## 폴더 구조 - Backend
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂web
 ┃ ┃ ┃ ┃ ┗ 📂curation
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AccountController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂feed
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FeedController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FeedDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDao.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChangepwdRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Feed.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FeedMapping.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Hospital.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SignupRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BasicResponse.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebCurationApplication.java
 ┃ ┗ 📂resources
 ┃ ┃ ┗ 📜application.properties
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂web
 ┃ ┃ ┃ ┃ ┗ 📂curation
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SkeletonTest2ApplicationTests.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebCurationApplicationTests.java
```




## Sub03 추가 예정 기능
*비급여 가격 정보*  
*야간 응급실 정보*  
*코로나 확진자 수 확인*  
*자주 찾은 병원(즐겨찾기 같은 느낌)*