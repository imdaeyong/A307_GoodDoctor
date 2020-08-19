<template>
  <div class="feed-modal">

    <b-container fluid class="bv-example-row">
      <b-row align-h="center">
        <b-col xl="5">
          <div class="feed-reply-modal">
            <div class="feed-top-modal">
              <img 
                :src="feed.user.imageUrl" 
                v-if="feed.user.imageUrl != null"
                class="profile-image"
                />
              <img src= "../../assets/images/profile_default.png" alt=""  v-else>
              <div class="user-info-modal">{{feed.user.nickname}}</div>
              <div class="user-hospital-modal">{{feed.hospital.name}} <span>{{feed.updateDate}}</span></div>
            </div>
            <div class="feed-card-modal">
              <img :src="feed.imageUrl" v-if="feed.imageUrl != null" class="feed-card-image"/><br>
              <star-rating :inline="true" style="float : right; font-size:1em" text-class="rating-text-modal" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :star-size="20" :show-rating="true" :read-only="true" :increment="0.5" :rating="feed.star">
              </star-rating>
              <div class="feed-content">
                <a href="">#진료잘봄#호감</a><br>
                <div style="padding : 0px;" v-if="plusContent">
                  <div class="text-truncate" style="width: 60%; padding : 0px;">
                    {{feed.content}}
                  </div>
                  <button><span v-if="feed.content.length > 30" @click="plusContent = false">더보기</span></button>
                </div>
                <div v-if="!plusContent">{{feed.content}}</div>
              </div>
            </div>
          </div>
        </b-col>
      </b-row>
    </b-container>


    <!-- 댓글창 -->
    <div class="feed-foot-modal">
      <div class="feed-reply-content">
                <div v-for="reply in replys" v-bind:key="reply.id">
          <div class="feed-reply">
            <div>
              <img :src="reply.imageUrl" v-if="reply.imageUrl != null"
                class="profile-image"/>
              <img src= "../../assets/images/profile_default.png" alt=""  v-else>
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
        <span v-show="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span> 
      </div>
      <div class ="reply-list">
        
        <img
          :src="user.imageUrl"
          v-if="user.imageUrl != null"
          class="profile-image"
        />
        <img src= "../../assets/images/profile_default.png" v-else alt="">
        <input @keypress.enter="addReply(feed.id, data)" type="text" class="reply-content" placeholder="댓글달기..." v-model="data">
        <button class="reply-submit" @click="addReply(feed.id, data)">게시</button> 
      </div>
    </div>
      
  </div>
</template>

<script>
import store from '@/vuex/store.js'
import http from '@/util/http-common'
import StarRating from 'vue-star-rating'

export default {
  name: "FeedModal",
  data: () => {
    return {
    replys : [],
    data : "",
    feed : store.state.feed,
    click : true,
    user: store.state.userInfo.data,
    plusContent : true,
    rating : 0
    }
  },
  components: {
    StarRating
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
        userId : this.user.id,
        feedId : feedId,
        content : feedData,
        nickname : store.state.userInfo.data.nickname,
        imageUrl : this.user.imageUrl
      };
      http.post(`comments/`,comment)
      .then(data => {
        alert("댓글등록 완료");
        this.replys = data.data;
        this.data = "";
      })
      .catch(err =>{

      })  
    },
   addLike(isClick, feedId){ //좋아요 버튼 클릭시 실행 함수
      if (this.click) {
        this.click = !this.click;
        http.put(`feeds/like`,{feedId:feedId, userId:store.state.userInfo.data.id, 
            isClick:isClick, type:"modal", size : 0, word:""})
        .then(data => {
          this.feed.likes = data.data.likes;
          this.click = true;
          this.$EventBus.$emit('updateLike', store.state.index);
          this.$EventBus.$emit('updateLikes', this.feed.likes);
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