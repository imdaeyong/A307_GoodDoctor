<template>
  <div>
    <NavBar/>

    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>

    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>
    <div>
      <div v-if="feeds.length == 0">
        <div class="feed-wrap">
          <div style="text-align:center"><h3><b-icon-emoji-frown animation="throb"></b-icon-emoji-frown>  QR코드를 안찍어서<br> 작성할 수 있는 피드가 없어요</h3></div>
        </div>
      </div>
      <!-- 기존에 내가 작성한 Feed목록 -->
      <div v-else>
      <div v-for="feed in feeds" v-bind:key="feed.id">
        <div v-if="feed.isNew">
          <div class="feed-wrap" v-bind:data="feed.data">
            <div class="feed-top">
              <img src= "../../assets/images/profile_default.png" alt="">
              <div class="user-info">{{feed.user.nickname}} <span style="color : red; font-weight : bold"> NEW !!!NEW !!!</span> </div>
              <div class="user-hospital">{{feed.hospital.name}}<span>{{feed.updateDate}}</span></div>
            </div>
            
            <div class ="review-write-form">
              <div v-if="openWrite == feed.id" class ="review-write">
                <input type="file" id="file" ref="file" v-on:change="upload" class="review-img-upload"/>
                <img :src="preview">
                <textarea name="" id="" cols="60%" rows="3" v-model="feed.review">

                </textarea><br>
                <button v-on:click="addReview(feed.id, feed.review)">작성</button>
              </div>
            </div>
          </div>
          <div class ="review-write-btn">
            <button v-if="openWrite != feed.id" v-on:click="feedWrite(feed.id)">작성하기</button>
            <button v-if="openWrite == feed.id" v-on:click="feedWrite('')">작성닫기</button>
          </div>
        </div>
        <div v-if="!feed.isNew">
          <div class="feed-wrap" v-bind:data="feed.data">
            <div class="feed-top">
              <img src= "../../assets/images/profile_default.png" alt="">
              <div class="user-info">{{feed.user.nickname}}</div>
              <div class="user-hospital">{{feed.hospital.name}}<span>{{feed.updateDate}}</span></div>
            </div>
            <div class="feed-card">
              <img src= "../../assets/images/feed/1.png" alt="">
              <a href="">#진료잘봄#호감</a><br>
              {{feed.content}}
              
              <span>더보기...</span>
            </div>
            <div class="feed-foot">
              <div class="feed-btn-list">
                <div class ="like"><button v-on:click="addLike()"><b-icon-heart ></b-icon-heart></button></div>
                <div class ="reply"><button><b-icon-chat-square v-on:click="openReply(feed.id)"></b-icon-chat-square></button></div>
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
      </div> 
    </div>




  </div>
</template>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.10/lodash.min.js"></script>

<script>
import NavBar from "../../components/NavigationBar.vue";
import User from "../../views/accounts/Login.vue";
import "../../assets/css/feedWrite.scss";
import axios from "axios";
import store from '@/vuex/store.js'
import http from '@/util/http-common'
import FeedModal from "../feed/FeedModal.vue";

export default {
  components: { 
    FeedModal,
    NavBar, 
    User 
  },
  data: () => {
    return {
      openWrite : "",
      feeds : [],
      nickname : "",
      userId : "",
      content : "",
      newImgSrc: '',
      file: '',
      img : '',
      preview : ''
    }
  },
   mounted(){
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
    else{this.nickname = store.state.userInfo.data.nickname;
      this.isLogin = store.state.isLogin;
      this.userId = store.state.userInfo.data.id
      http.get(`feeds/write/${this.userId}`)
      .then(data => {
        this.feeds = data.data;
        console.log(this.feeds);
      })
    }
  },
  methods:{
    addLike(){ //좋아요 버튼 클릭시 실행 함수
      alert("하이");
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
        content : feedData
      };
      http.post(`comments/`,comment)
      .then(data =>{
        alert("댓글등록 완료");
        
        //this.$router.go(0);
      })
      .catch(err =>{

      })
    },
    feedWrite(id){
      //id를 받아서 펼치게 될 경우를 정해준다.
      this.openWrite = id;
    },
    addReview(feedId, reviewData){
      
      http.put(`feeds/`,{id:feedId, content:reviewData})
      .then(data =>{
        alert("리뷰작성 완료");
      })
      .catch(err =>{

      })

      ////////////////////////////////
      let formData = new FormData();
      formData.append('file', this.file);
      console.log(formData);
      http.post(`feeds/`, formData,{
        headers:{'Content-Type':'multipart/form-data'}
      })
      .then(data =>{
        alert("이미지업로드 완료");
        this.$router.go(0);
      })
      .catch(err =>{

      })
    },
    upload(e){
      let file = e.target.files[0];
      this.file = file;
      
      this.img = require('C:/temptemp/'+file.name);
      this.preview = URL.createObjectURL(file);
      console.log(this.preview);


      let reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = e => {
        this.preview = e.target.result;
        console.log(e.target.result);
      }
      console.log(file);
    },


  }
}
</script>