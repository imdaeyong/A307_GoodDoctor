<template>
  <div>
    <h1 class="mt-4">소중한 내 정보 지켜줄게요</h1>
    <b-row class="mt-4">
      <div class="ml-5 mr-3">
        <img  src= "../../../assets/images/profile_default.png" alt="" style="width: 14rem">
        <div class="user-info-modal mt-3">닉네임</div>
        <div class="user-info-modal mt-3"><a href="#" style="color : #17a2b8">아이콘 변경</a></div>
      </div>
      <b-col class="right mt-3">
        <h3> - 닉네임: <span  style="color : #17a2b8">{{this.$store.state.userInfo.data.nickname}}</span></h3>
        <h3 class="mt-5"> - 아이디(이메일): <span style="color : #17a2b8">{{this.$store.state.userInfo.data.email}}</span></h3>
        <div class="row mt-5">
          <a href="/accounts/changepassword"><button class="myProfile"><h3>비밀번호 변경</h3></button></a>
          <a href="/accounts/delete"><button class="myProfile left"><h3>탈퇴하기</h3></button></a>
        </div>
        </b-col>
    </b-row>
  </div>
</template>

<script>
import '../../../assets/css/style.scss'
import store from '../../../vuex/store.js'

export default {
    name: "myProfile",
}
</script>

<style>

</style>