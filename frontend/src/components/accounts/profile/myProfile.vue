<template>
  <div>
    <h1 class="mt-4">소중한 내 정보 지켜줄게요</h1>
    <b-row class="mt-4">
      <div class="ml-5 mr-3">
        <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" 
        style="width: 14rem; border-radius: 100px 100px 100px 100px;">
        <img :src="user.imageUrl" v-else-if="preview != null" class="profile-image" 
        style="width: 14rem; border-radius: 100px 100px 100px 100px;">
        <img  src= "../../../assets/images/profile_default.png" alt="" v-else style="width: 14rem">
        <div class="user-info-modal mt-3">닉네임</div>
        <input type="file" id="file" ref="file" v-on:change="upload" class="review-img-upload"/>
        <div class="user-info-modal mt-3"><a href="" @click="changeProfile(feed.id, feed.data)" style="color : #17a2b8">프로필 변경</a></div>
      </div>
      <b-col class="right mt-3">
        <h3> - 닉네임: <span  style="color : #17a2b8">{{user.nickname}}</span></h3>
        <h3 class="mt-5"> - 아이디(이메일): <span style="color : #17a2b8">{{user.email}}</span></h3>
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
    data: () => {
      return {
        user : store.state.userInfo.data,
        preview : ''
      }
    },
    methods:{
      upload(e){
        let file = e.target.files[0];
        this.file = file;

        this.preview = URL.createObjectURL(file);
        let reader = new FileReader();

        reader.readAsDataURL(file);
        reader.onload = e => {
          this.preview = e.target.result;
        }
      }
    }
}
</script>

<style>

</style>