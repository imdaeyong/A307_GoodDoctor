<template>
  <div class="user" id="join">
    <div class="wrapCC mt-5">
      <h1>비밀번호를 찾아요</h1>
      <div class="input-label">
        <input 
          v-model="email" 
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          id="email" 
          placeholder="가입시 사용한 이메일을 입력." 
          type="text"/>
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <button 
        class="btn-full-center mt-2"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
        @click="sendPwdToEmail"
        >
      비밀번호 보내기</button>
    </div>   
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import http from '@/util/http-common'
import * as EmailValidator from "email-validator"

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
    sendPwdToEmail() {
      http.post(`pwd`, {email: this.email})
        .then(res => {
          if(res.data.accountType==0){
            alert(this.email+'로 비밀번호를 보냈습니다.')
          }else if(res.data.accountType==1){
            alert("구글 간편 로그인 계정입니다. 구글 로그인해주세요!")
          }else {
            alert("카카오 간편 로그인 계정입니다. 카카오 로그인해주세요!")
          }
          this.$router.push("/feed/main");
        })
        .catch(err => {
          console.error(err) // 오류 내역 확인
          alert('이메일 확인 부탁드립니다.')
        })
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