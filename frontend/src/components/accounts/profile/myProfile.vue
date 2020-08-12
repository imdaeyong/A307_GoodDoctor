<template>
  <div>
    <h1 class="mt-4">소중한 내 정보 지켜줄게요</h1>
    <b-row class="mt-4">
      <div class="ml-5 mr-3">
        <img :src="preview" v-if="preview != null" class="profile-image" 
        style="width: 14rem; height: 16rem; border-radius: 100px 100px 100px 100px;">
        <img  src= "../../../assets/images/profile_default.png" alt="" v-else style="width: 14rem">
        <br><br>
        <div class="user-info-modal mt-3">
          <input type="file" id="file" ref="file" v-on:change="upload" class="review-img-upload" accept="image/png, image/jpg, image/gif, image/jpeg"/>
        </div>
        <div class="user-info-modal mt-3"><button @click="changeProfile()" style="color : #17a2b8; font-size : 1.2em; font-weight : bold;">프로필 변경</button></div>
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
import http from "@/util/http-common";
import router from '../../../router';

export default {
    name: "myProfile",
    data: () => {
      return {
        user : store.state.userInfo.data,
        preview : store.state.userInfo.data.imageUrl
      }
    },
    mounted(){
      this.user = store.state.userInfo.data,
      this.preview = store.state.userInfo.data.imageUrl
    },
    methods:{
      changeProfile() {
        let formData = new FormData();
        formData.append("id", this.user.id);
        formData.append("file", this.file);

        http
        .put(`profile`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((data) => {
          console.log(data.data);
          this.$store.commit('mutateUserInfo',data);
          alert("프로필 사진 변경완료");
          this.$router.go(0);
        })
        .catch((err) => {
          alert("프로필 사진 변경실패");
        })
      },
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