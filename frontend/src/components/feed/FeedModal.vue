<template>
  <div class="feed-modal">
    <div class="feed-reply-modal">
      <div class="feed-top-modal">
        <img src= "../../assets/images/profile_default.png" alt="">
        <div class="user-info-modal">{{feed.user.nickname}}</div>
        <div class="user-hospital-modal">{{feed.hospital.name}} <span>{{feed.updateDate}}</span></div>
      </div>
      <div class="feed-card-modal">
        <img src= "../../assets/images/feed/1.png" alt=""><br>
        <div>
          <a href="">#진료잘봄#호감</a><br>
          {{feed.content}}
          <span>더보기...</span>
        </div>
      </div>
    </div>
    <div class="feed-foot-modal">
      <div class="feed-reply-content">
        <div v-for="reply in replys" v-bind:key="reply.id">
          <div class="feed-reply">
            <div>
              <img src= "../../assets/images/profile_default.png" alt="">
              <!-- <h1>{{feed}}</h1> -->
              <div class="user-info">{{reply.nickname}}</div><br>
            </div><br>
            <div class="reply">{{reply.content}}</div>
            <div class="reply-time"><span>{{time(reply.createDate)}}</span></div>
          </div>
        </div>
      </div>
      <div class="feed-btn-list">
        <div class ="like" @click="addLike(feed.isClick, feed.id)">
          <button id="heart" style="outline : 0;">
            <b-icon-heart v-show="!feed.isClick"></b-icon-heart>
            <b-icon-heart-fill class = "f-heart" v-show="feed.isClick"  style="color : red;"></b-icon-heart-fill>
          </button>
        </div>
        <div class ="share"><button><b-icon-reply @click="addShare()"></b-icon-reply></button></div>
        <span v-if="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span> 
      </div>
      <div class ="reply-list">
        
        <img
          :src="user.imageUrl"
          v-if="user.imageUrl != null"
          class="profile-image"
        />
        <img src= "../../assets/images/profile_default.png" v-else alt="">
        <input type="text" class="reply-content" placeholder="댓글달기..." v-model="data">
        <button class="reply-submit" @click="addReply(feed.id, data)">게시</button> 
      </div>
    </div>
      
  </div>
</template>

<script>
import store from '@/vuex/store.js'
import http from '@/util/http-common'

export default {
  name: "FeedModal",
  data: () => {
    return {
    replys : [],
    data : "",
    feed : store.state.feed,
    click : true,
    user: store.state.userInfo.data
    }
  },
  mounted(){
    http.get(`comments/${store.state.feed.id}`)
    .then(data => {
      this.replys = data.data; //해당 댓글 정보들을 가져온다.
    })
    .catch(err =>{
        alert("댓글이 아직 없습니다.");
    }) 
  },
  methods:{
    addReply(feedId, feedData){
      let comment = {
        userId : this.feed.user.id,
        feedId : feedId,
        content : feedData,
        nickname : store.state.userInfo.data.nickname,
      };
      http.post(`comments/`,comment)
      .then(data => {
        alert("댓글등록 완료");
        http.get(`comments/${store.state.feed.id}`).then(data => {
          this.replys = data.data; //해당 댓글 정보들을 가져온다.
          this.data = "";
        }) 
      })
      .catch(err =>{

      })  
    },
    addLike(isClick, feedId){ //좋아요 버튼 클릭시 실행 함수
      if (this.click) {
        this.click = !this.click;
        http.put(`feeds/like`,{feedId:feedId, userId:this.feed.user.id, 
            isClick:isClick, type:"modal", size : 0, word:""})
        .then(data => {
          this.feed = data.data;
          this.click = true;
        })
      }
    },
    time(createDate){
        var date1 = new Date(createDate);
        var date2 = new Date();
        var result = (date2 - date1) / 1000
        if(result < 60) {
            return parseInt(result)+"초전"
        }
        if(result < 60*60) {
            return parseInt(result/60)+"분전" //60분 이하면 분 넘겨준다.
        }
        else if(result < 24*60*60) {
            return parseInt(result/60/60)+"시간전" //24시간 이하면 시간 넘겨준다.
        }
        else if(result < 24*60*60*30) {
            return parseInt(result/24/60/60)+"일전" //30일 이하면 일 넘겨준다.
        }
        else if(result < 24*60*60*30*12) {
            return parseInt(result/24/30/60/60)+"개월전" //12개월 이하면 월 넘겨준다.
        }
        else {
            return parseInt(result/24/30/60/60/12)+"년전"
        }
    }
  }  
}
</script>