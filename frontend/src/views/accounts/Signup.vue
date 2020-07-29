
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user" id="login">
    <div class="wrapC mt-5">
      <h1>가입하기</h1>
      <div class="form-wrap">

        <div class="input-label">
          <input 
            v-model="nickName" 
            id="nickName" 
            placeholder="닉네임을 입력하세요." 
            type="text" 
          />
          <label for="nickName">닉네임</label>
        </div>

        <div class="input-label">
          <input 
            v-model="email" 
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
            id="email" 
            placeholder="이메일을 입력하세요." 
            type="text" />
          <label for="email">이메일</label>
          <label for="email" @click="emailAuthStart" class="right">인증 하기</label>
          <div class="error-text" v-if="error.email">{{error.email}}</div>
        </div>

        <div class="half" v-if="emailAuthinput">
          <div class="input-label">
            <input 
              type="text"
              id ="inputAuth"
              ref="inputAuth"
              v-model="inputAuth"
              v-bind:class="{complete:inputAuth.length==6}"
            />
            <label for="inputAuth">인증번호</label>
            <label for="inputAuth" @click="emailAuthCheck" class="authRight"><button><span style="font-weight: bold">확인</span></button>
              </label>
          </div>
          <br>
          <b>전송된 인증번호 : {{this.$store.state.authCode}}</b>
        </div>


        <div class="input-label">
          <input 
            v-model="password"
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
            id="password" 
            :type="passwordType" 
            placeholder="비밀번호를 입력하세요." />
          <label for="password">비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>

        <div class="input-label">
          <input
            v-model="passwordConfirm"
            :type="passwordConfirmType"
            v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
            id="password-confirm"
            placeholder="비밀번호를 다시한번 입력하세요."
          />
          <label for="password-confirm">비밀번호 확인</label>
          <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
        </div>
      </div>

      <!-- <label>
        <input v-model="isTerm" type="checkbox" id="term" />
        <span>약관을 동의합니다.</span>
      </label>
      <span @click="termPopup=true">약관보기</span> -->

      <button class="btn-full-center mt-4"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
      @click="onjoin"
      >가입하기</button>
      
    </div>
  </div>
</template>

<script>
const SERVER_URL="http://localhost:8080/"
import * as EmailValidator from "email-validator"
import PV from "password-validator"
import UserApi from "../../api/UserApi"
import axios from 'axios'
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
//이메일 인증 관련 import
import http from '@/util/http-common'
import store from '@/vuex/store.js'

export default {
  name: "Singup",
  data: () => {
    return {
      nickName: "",
      email: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      passwordType: "password",
      passwordConfirmType: "password",
      isTerm: false,
      isLoading: false,
      error: {
        nickName: false,
        email: false,
        password: false,
        passwordConfirm: false,
        authEmail: false,
      },
      isSubmit: false,
      termPopup: false,
      //인증 관련 변수
      authCode:"",
      inputAuth:"",
      emailAuthinput: false,
    };
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
    email: function(v) {
      this.emailCheckForm();
    },
    password: function(v) {
      this.passwordCheckForm();
    },
    passwordConfirm: function() {
      this.passwordConfirmCheckForm()
    }
  },
  methods: {
    emailAuthCheck() {
      console.log(this.$store.authCode);
      if(this.$store.state.authCode!=this.inputAuth){
        alert("인증번호를 다시 확인해주세요!");
      }else{
        alert("인증 되었습니다.")
      }
    },
    emailAuthStart() {
      this.emailAuthinput = true
      console.log("이메일 인증 시작",this.emailAuthStart)
      http
        .post("/email", {email: this.email})
        .then(({ data }) => {
          this.$store.state.authCode=data.object
        })
        .catch(() => {
          console.log("emailCheckError");
        });
    },
    emailCheckForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
    },
    passwordCheckForm() {
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문, 숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
    },
    passwordConfirmCheckForm() {
      if (this.password !== this.passwordConfirm) {
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다."
      }
      else this.error.passwordConfirm = false;
    
      let isSubmit = true;
        Object.values(this.error).map(v => {
          if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;    
    },


    onjoin(){
      axios.post(`${SERVER_URL}account`,{nickname: `${this.nickName}`, email : `${this.email}` , password :`${this.password}`})
      .then(res=>{
        this.$router.push("/emailCheck");
        alert("이메일 인증 페이지로 넘어갑니다.");
      })
      .catch(err=>{
        if(err.response.data.data == "nickname_fail") alert("이미 존재하는 닉네임입니다.");
        else if(err.response.data.data == "email_fail") alert("이미 존재하는 이메일입니다.");
      })
    }
  }
};
</script>
