<template>
  <div class="user" id="join">
    <div class="wrapC">
      <h1>이메일 인증</h1>
      <div class="input-with-label">
        <input type="text"
           id ="inputAuth"
           ref="inputAuth"
          v-model="inputAuth"
          placeholder="인증번호 입력"
        />
      </div>
      <br>
      
      <br>
      <button class="btn btn--back btn--login" @click="emailCheck">인증번호 확인</button>
    </div>
  </div>

</template>

<script>
import "../../components/css/user.scss";
export default {
  data: () => {
    return {
      email: "",
      authCode:"",
      inputAuth:""
    };
  },
  methods: {
    emailCheck() {
      //this.$router.push("/");
      console.log(this.$store.authCode);
      if(this.$store.state.authCode!=this.inputAuth){
        alert("인증번호를 다시 확인해주세요!");
      }else{
        this.$router.push("/joinComplete");
      }
    }
  }
};
</script>

<style scoped>
</style>