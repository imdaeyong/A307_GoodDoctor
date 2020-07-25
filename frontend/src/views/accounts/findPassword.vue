<template>
  <div class="user" id="join">
    <div class="wrapC mt-5">
      <h1>비밀번호를 찾아요</h1>

        <div class="input-label">
          <input 
            v-model="email" 
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
            id="email" 
            placeholder="이메일을 입력하세요." 
            type="text"/>
          <label for="email">이메일</label>
          <div class="error-text" v-if="error.email">{{error.email}}</div>
        </div>

        <button 
          class="btn-full-center mt-2"
          :disabled="!isSubmit"
          :class="{disabled : !isSubmit}"
          @click="emailAuthStart"
          >
        비밀번호 보내기</button>
    </div>   
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import axios from 'axios'
import http from '@/util/http-common'
import * as EmailValidator from "email-validator"

const SERVER_URL="http://localhost:8080/"

export default {
  name: "findPassword",
  data: () => {
    return {
      email: "",
      error: {
        email: false,
      },
      isSubmit: false,
    }
  },
  watch: {
    email: function(v) {
      this.emailCheckForm();
    }
  },
  methods: {
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

      let isSubmit = true;
        Object.values(this.error).map(v => {
          if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;
    },
  }
}
</script>