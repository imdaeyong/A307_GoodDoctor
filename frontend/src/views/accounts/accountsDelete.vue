<template>
  <div class="user" id="login">
    <div class="wrapC mt-5">
      <h1>탈퇴하기</h1>
      <div class="input-label">
        <input 
          v-bind:class="{complete:nickName.length!==0}"
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
          type="text"/>
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <button class="btn-full-center mt-4"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
      @click="accountsDelete"
      >탈퇴하기</button>
    </div>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator"
import http from '@/util/http-common'
import store from '../../vuex/store.js'

export default {
  name: "accountsDelete",
  created() {
    this.id = store.state.userInfo.data.id
  },
  watch: {
    email: function() {
      this.emailCheckForm()
    },
  },
  data() {
    return {
      id: "",
      nickName: "",
      email: "",
      error: {
        email: false,
      },
      isSubmit: false
    }
  },
  methods: {
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
    accountsDelete() {
      http.delete(`/account`, {params:{id: this.id, nickname: this.nickName, email: this.email}})
        .then(res => {
          alert('탈퇴가 정상적으로 처리 되었습니다.')
          store.dispatch('logout');
          this.$router.push('/feed/main')
          this.$router.go(0);
        })
        .catch(err => {
          alert('오류가 발생했습니다. 다시 시도해 주세요.')
        })
    }
  }
}
</script>

<style>

</style>