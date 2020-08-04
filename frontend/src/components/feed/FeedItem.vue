<template>
  <div class="feed-item">
    <div v-for="(feed, index) in feeds.data" v-bind:key="feed.id" v-bind:index="index">
      <div v-if="!feed.isNew">
        <div class="feed-wrap" v-bind:data="feed.data">
          <div class="feed-top">
            <img src= "../../assets/images/profile_default.png" alt="">
            <div class="user-info">{{feed.user.nickname}}</div>
            <div class="user-hospital">{{feed.hospital.name}}<span>{{feed.updateDate}}</span></div>
          </div>
          <div class="feed-card">
            <img src="../../assets/images/feed/1.png" alt="">
            <a href="">#진료잘봄#호감</a><br>
            {{feed.content}}
            
            <span>더보기...</span>
          </div>
          <div class="feed-foot">
            <div class="feed-btn-list">
              <div class ="like"><button v-on:click="addLike(feed.isClick, index, feed.id)">
                <b-icon-heart v-if="!feed.isClick"></b-icon-heart>
                <b-icon-heart-fill v-else></b-icon-heart-fill></button>
              </div>
              <div class ="reply"><button><b-icon-chat-square v-on:click="openReply(feed.id)"></b-icon-chat-square></button></div>
              <div class ="share"><button><b-icon-reply v-on:click="addShare()"></b-icon-reply></button></div>
              <span v-if="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span> 
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
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import axios from "axios";
import store from '@/vuex/store.js'
import http from '@/util/http-common'

export default {
  data: () => {
    return {
      feeds : [],
      nickname : "",
      isLogin : false,
      userId : "",
      content : "",
      likes : []
    }
  },
  mounted(){
    this.userId = store.state.userInfo.data.id
    http.get(`feeds/${this.userId}`).then(data => {
      this.feeds = data;
      this.nickname = store.state.userInfo.data.nickname;
      this.isLogin = store.state.isLogin;
    })

  },
  methods:{
    addLike(isClick, index, feedId){ //좋아요 버튼 클릭시 실행 함수
      //만약에 isLike가 false라면
      http.put(`feeds/like`,{feedId:feedId, userId:this.userId, isClick:isClick})
      .then({
      
      })
      http.get(`feeds/${this.userId}`).then(data => {
      this.feeds = data;
      this.nickname = store.state.userInfo.data.nickname;
      this.isLogin = store.state.isLogin;
      })
    },
    openReply(feedInfo){ //댓글 버튼 클릭시 실행 함수
      store.dispatch('openReply', feedInfo);
      this.$bvModal.show('bv-modal-feed');
    },
    addShare(){ //공유버튼 클릭시 실행 함수
      alert("하이");
    },
    addReply(feedId, feedData){
      let comment = {
        userId : this.userId,
        feedId : feedId,
        content : feedData,
        nickname : this.nickname
      };
      http.post(`comments/`,comment)
      .then(data =>{
        alert("댓글등록 완료");
        //this.$router.go(0);
      })
      .catch(err =>{

      })  
    }
  }
};
</script>
