<template>
  <div class="user" id="join">
    <div class="wrapC">
      <h1>이메일 인증</h1>
      <div class="input-with-label">
        <input type="email" 
        id ="email"
        ref="email"
        v-model="email"
        placeholder="이메일을 입려하세요"
        />
      </div>
      <button @click="createHandler">인증번호 받기</button>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common'
import store from '@/vuex/store.js';

export default {
  data:() =>{
    return {
      email: "",
      authCode:"",

    };
  },
  methods: {
    createHandler() {
      http
        .post("/email", {email: this.email})
        .then(({ data }) => {
          this.$store.state.authCode=data.object;
          // console.log("emailCheck");
          console.log(data);
          console.log(this.$store.state.authCode);
          this.$router.push("/emailSend");
        })
        .catch(() => {
          console.log("emailCheckError");
        });
    }
  }
};
</script>

<style scoped>
</style>