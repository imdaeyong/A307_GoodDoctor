<template>
  <div class="feed-item">
    <div v-for="feed in feeds.data" v-bind:key="feed.id">
      <div class="feed-wrap" v-bind:data="feed.data">
        <div class="feed-top">
          <img src= "../../assets/images/profile_default.png" alt="">
          <div class="user-info">{{feed.user.nickname}}</div>
          <div class="user-hospital">{{feed.hospital.name}}<span>2020.07.13</span></div>
        </div>
        <div class="feed-card">
          <img src= "../../assets/images/feed-sample.jpg" alt="">
          <a href="">#새로나온차#미래내차</a><br>
          {{feed.content}}
          
          <span>더보기...</span>
        </div>
        <div class="feed-foot">
          <div class="feed-btn-list">
            <div class ="like"><button v-on:click="addLike()"><b-icon-heart ></b-icon-heart></button></div>
            <div class ="reply"><button><b-icon-chat-square v-on:click="openReply()"></b-icon-chat-square></button></div>
            <div class ="share"><button><b-icon-reply v-on:click="addShare()"></b-icon-reply></button></div>
          </div>
          <div class ="reply-list">
            <img src= "../../assets/images/profile_default.png" alt="">
            <div class="user-info">
              <span v-if="isLogin"> {{nickname}}</span> 
              <span v-else> 닉네임</span>
            </div>
            <input type="text" class="reply-content" placeholder="댓글달기..." v-model="feed.data">
            <button class="reply-submit" v-on:click="addReply(feed.id, feed.data)">게시</button> 
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const SERVER_URL="http://localhost:8080/"
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import axios from "axios";
import store from '@/vuex/store.js'
export default {
  data: () => {
    return {
      feeds : [],
      nickname : "",
      isLogin : false,
      userId : "",
      content : ""
    }
  },
  mounted(){
    axios.get(`${SERVER_URL}feeds/`).then(data => {
      this.feeds = data;
      this.nickname = store.state.userInfo.data.nickname;
      this.isLogin = store.state.isLogin;
      this.userId = store.state.userInfo.data.id
    })
  },
  methods:{
    addLike(){ //좋아요 버튼 클릭시 실행 함수
      alert("하이");
    },
    openReply(){ //댓글 버튼 클릭시 실행 함수
      this.$bvModal.show('bv-modal-feed');
    },
    addShare(){ //공유버튼 클릭시 실행 함수
      alert("하이");
    },
    addReply(feedId, feedData){
      let comment = {
        userId : this.userId,
        feedId : feedId,
        content : feedData
 
      };
      axios.post(`${SERVER_URL}comments/`,comment)
      .then(data =>{
        alert("댓글등록 완료");
        this.$router.go(0);
      })
      .catch(err =>{

      })
      
      
    }
  }
};
</script>
