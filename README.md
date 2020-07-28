# Sub PJT 02 ( 07/20 ~ 07/31 ) - 굿닥터

## 목표
SNS의 기본 기능을 완성시키고, 의료 SNS에 맞는 세부기능 구현

# 기획
### 와이어프레임
[이동하기](https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a307/blob/develop/doc/_%EC%84%9C%EC%9A%B8A307_%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84.pdf)

### ERD (추후 수정 요망)
[이동하기](https://lab.ssafy.com/s03-webmobile2-sub2/s03p12a307/blob/develop/doc/_%EC%84%9C%EC%9A%B8_A307_Database_ERD.png)

---

# 테스트하기

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

# 서비스 설명
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

## 사용법 ( AWS ) - 추가예정


## Sub03 추가 예정 기능
*비급여 가격 정보*  
*야간 응급실 정보*  
*코로나 확진자 수 확인*  
*자주 찾은 병원(즐겨찾기 같은 느낌)*