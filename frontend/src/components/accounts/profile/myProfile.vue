<template>
  <div>
    <h1 class="mt-3">소중한 내 정보</h1>
    <b-row>
      <div class="ml-4">
        <img  src= "../../../assets/images/profile_default.png" alt="" style="width: 14rem">
        <div class="user-info-modal">닉네임</div>
      </div>
      <b-col class="right">
        <h3>닉네임: <span  style="color : #17a2b8">{{this.$store.state.userInfo.data.nickname}}</span></h3>
        <h3 class="mt-5">아이디(이메일): <span style="color : #17a2b8">{{this.$store.state.userInfo.data.email}}</span></h3>
        <h3 class="mt-5"><a href="/accounts/changepassword"><span style="color : #17a2b8">비밀번호 변경</span></a></h3>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import store from '../../../vuex/store.js'
import '../../../assets/css/style.scss'

export default {
    name: "myProfile",
}
</script>

<style>

</style>