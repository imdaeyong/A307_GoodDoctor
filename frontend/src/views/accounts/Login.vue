<template>
  <b-container class="modalSize text-left">
    <h1 class="mt-5 font-weight-bold">
      로그인을 하고 나면
      <br />좋은 일만 있을 거예요.
    </h1>

    <div class="mt-3">
      <b-input-group>
        <template v-slot:prepend>
          <b-input-group-text class="inputLabel">이메일</b-input-group-text>
        </template>
        <b-form-input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          @keyup.enter="Login"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
        ></b-form-input>
      </b-input-group>
    </div>

    <div class="mt-3">
      <b-input-group>
        <template v-slot:prepend>
          <b-input-group-text class="inputLabel">비밀번호</b-input-group-text>
        </template>
        <b-form-input
          v-model="password"
          type="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요."
        ></b-form-input>
      </b-input-group>
    </div>

    <!-- 로그인 버튼 -->
    <div class="mt-3 text-center">
      <button
        class="btn"
        @click="onLogin"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >로그인</button>
    </div>

    <hr>
    <b-container fluid>
      <div class="text">
        <p>SNS 간편 로그인</p>
      </div>
      <div class="d-flex justify-content-end sns-login">
        <kakaoLogin  :component="component" />
        <GoogleLogin :component="component" />
      </div>
    </b-container>
    <hr>
    
    <b-container>
      <div class="text">
        <p>혹시</p>
      </div>
      <div class="d-flex">
        <p class="font-weight-bold ">비밀번호를 잊으셨나요?</p>
        <router-link to="/accounts/changePassword">비밀번호 찾기</router-link>
      </div>
      <div class="d-flex">
        <p class="font-weight-bold">아직 회원이 아니신가요?</p>
        <router-link to="/accounts/signin">가입하기</router-link>
      </div>
    </b-container>
    
  </b-container>   
</template>

<script>
const SERVER_URL="http://localhost:8080/"
import axios from 'axios'
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/accounts/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/accounts/snsLogin/Google.vue";

export default {
  components: {
    KakaoLogin,
    GoogleLogin
  },
  created() {
    this.component = this;

    this.passwordSchema
    .is()
    .min(8)
    .is()
    .max(100)
    .has()
    .digits()
    .has()
    .letters();
    },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin(){
      let { email, password } = this;
      let data = {
        email,
        password
      };
      axios.get(`${SERVER_URL}account/login?email=${this.email}&password=${this.password}`)
        .then(res=>{
            console.log(res);
            this.$session.start();
            this.$session.set('user', data);
            //Vue.http.headers.common['Authorization'] = 'Bearer ' + res.body.token;

            this.$router.push("/feed/main");
            console.log(this.$session.get('user'));
            alert("로그인 성공");
            
          }
        )
        .catch(err=>{
            console.log(err);
            alert("아이디 또는 비밀번호 실패입니다.");
            this.$router.push("/errorPage");
        })
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this
    };
  }
}

</script>

<style scoped>
.modalSize{
  width: 570px;
}
.inputLabel{
  width: 130px;
}

</style>