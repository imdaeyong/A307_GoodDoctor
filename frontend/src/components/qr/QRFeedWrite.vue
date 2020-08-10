<template>
  <div>    
  </div>
</template>
<script>
import NavBar from "../../components/NavigationBar.vue";
import User from "../../views/accounts/Login.vue";
import store from '@/vuex/store.js'
import http from '@/util/http-common'
import FeedModal from "../feed/FeedModal.vue";

export default {
  mounted(){
    if (this.$store.state.isLogin){
      this.hospitalId = this.$route.query.hospitalId;
      http.post(`/qr/wologin?hospitalId=${this.hospitalId}&userId=${this.$store.state.userInfo.data.id}`).then(res =>{
        this.$router.push(`../feed/write`);
        this.$router.go(0);
      }).catch(err => {
        alert("잘못된 QR코드입니다! 다시 등록해주세요!");
        this.$router.push(`../errorPage`);
      })
    }
  },
}
</script>