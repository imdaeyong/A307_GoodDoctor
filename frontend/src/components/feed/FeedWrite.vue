<template>
  <div>
    <NavBar />
    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>
    <div style="text-align:center" v-if="!this.loaded"><img src = "../../assets/images/bonoloading.gif"/> </div>
    <div style="margin-top : -40px;">
    <div v-if="this.loaded &&this.loading&& feeds.length == 0">
      <b-container fluid class="bv-example-row">
        <b-row align-h="center">
          <b-col xl="5">
            <div class="feed-wrap" >
              <div style="text-align:center">
                <h3>
                  <b-icon-emoji-frown animation="throb"></b-icon-emoji-frown>QR코드를 안찍어서
                  <br />작성할 수 있는 피드가 없어요
                </h3>
              </div>
            </div>
          </b-col>
        </b-row>
      </b-container>
    </div>
      <!-- 기존에 내가 작성한 Feed목록 -->
      <div v-else>
        <b-container fluid class="bv-example-row">
          <b-row align-h="center">
            <b-col xl="5">
              <div v-for="(feed, index) in feeds" v-bind:key="feed.id">
                <div v-if="feed.isNew">
                  <img src="../../assets/images/new_logo.png" alt="" width="90px" height="40px;"
                      style="position : relative; top : 15px; z-index : 1">
                  <div class="feed-wrap">
                    <div class="feed-top">               
                      <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                      <img src="../../assets/images/profile_default.png" alt v-else />
                      <div class="user-info">
                        {{feed.user.nickname}}
                      </div>
                      <div class="user-hospital">
                        {{feed.hospital.name}}
                        <span>{{formatDate(feed.updateDate)}}</span>
                      </div>
                    </div>

                    <div class="review-write-form">
                      <div v-if="openWrite == feed.id" class="review-write">
                        <div class ="box-file-input">
                          <label>
                            <input
                              type="file"
                              id="file"
                              ref="file"
                              v-on:change="upload"
                              class="review-img-upload file-input"
                              accept="image/*"
                            />
                          </label>
                          
                        </div>
                        <img :src="preview" />
                        <star-rating 
                          :inline="true" 
                          text-class="rating-text" 
                          style="float : right; height : 40px; margin-right : 0.5em" 
                          border-color="#d8d8d8" 
                          :rounded-corners="true" 
                          :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" 
                          :increment="0.5" 
                          :star-size="20" 
                          @rating-selected="setRating" 
                        >
                        </star-rating>
                        <textarea name id cols="60%" style="width:100%" rows="3" v-model="feed.review"></textarea>
                        <button v-on:click="addReview(feed.id, feed.review)" class="feed-btn-full-center">작성</button>
                      </div>
                    </div>
                  </div>
                  <div class="review-write-btn">
                    <button v-if="openWrite != feed.id" v-on:click="feedWrite(feed)">작성하기</button>
                    <button v-if="openWrite == feed.id" v-on:click="feedWrite('')">작성닫기</button>
                  </div>
                </div>
                <div v-if="!feed.isNew">
                  <div class="feed-wrap">
                    <div class="feed-top">
                      <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                      <img src="../../assets/images/profile_default.png" alt v-else />
                      <div class="user-info">{{feed.user.nickname}}</div>
                      <div class="user-hospital">
                        {{feed.hospital.name}}
                        <span>{{formatDate(feed.updateDate)}}</span>
                      </div>
                    </div>
                    <div class="feed-card">
                      <img :src="feed.imageUrl" v-if="feed.imageUrl != null"/>
                      <div class="feed-content">
                        <br />
                        <div style="padding : 0px;" v-if="plusContent">
                          <div class="text-truncate" style="width: 60%; padding : 0px;">
                            {{feed.content}}
                          </div>
                          <button><span v-if="feed.content.length > 30" @click="plusContent = false">더보기</span></button>
                        </div>
                        <div v-if="!plusContent">{{feed.content}}</div>
                      </div>
                    </div>
                    <div class="feed-foot">
                      <div class="feed-btn-list">
                        <div class="like" @click="addLike(feed.isClick, feed.id, index)">
                          <button id="heart" style="outline : 0;">
                            <b-icon-heart v-show="!feed.isClick"></b-icon-heart>
                            <b-icon-heart-fill class="f-heart" v-show="feed.isClick" style="color : red;"></b-icon-heart-fill>
                          </button>
                        </div>
                        <div class="reply">
                          <button>
                            <b-icon-chat-square v-on:click="openReply(feed, index)"></b-icon-chat-square>
                          </button>
                        </div>
                        <star-rating 
                          :inline="true" 
                          style="float : right; height : 30px; font-size:1em" 
                          text-class="rating-text-write" 
                          border-color="#d8d8d8" 
                          :rounded-corners="true" 
                          :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" 
                          :star-size="20" 
                          :show-rating="true" 
                          :read-only="true" 
                          :increment="0.5" 
                          :rating="feed.star"
                        >
                        </star-rating>
                        <div class ="share">
                          <button>
                            <b-icon-pencil-square v-if="userId == feed.user.id && feed.modify == 1" @click="modifyContent(feed)"></b-icon-pencil-square>
                            <b-icon-pencil-square v-else style="color : rgba(194, 183, 183, 0.9); " @click="modifyContent(feed)"></b-icon-pencil-square>
                          </button>
                        </div>
                        <span v-show="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span>
                      </div>
                      <div class="reply-list">
                        <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                        <img src="../../assets/images/profile_default.png" alt v-else />
                        <input
                          type="text"
                          class="reply-content"
                          placeholder="댓글달기..."
                          v-model="feed.data"
                          @keypress.enter="addReply(feed.id, feed.data, index)"
                        />
                        <button class="reply-submit" v-on:click="addReply(feed.id, feed.data, index)">게시</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </b-col>
          </b-row>
        </b-container>
      </div>
      <div v-if="this.loaded"> 
      <infinite-loading @infinite="infiniteHandler" spinner="bubbles">
        <div
          slot="no-results"
          style="color: rgb(102, 102, 102); font-size: 20px; padding: 10px 0px;"
        >작성한 피드가 없습니다 :)</div>
        <div
          slot="no-more"
          style="color: rgb(102, 102, 102); font-size: 20px; padding: 10px 0px;"
        >목록의 끝입니다 :)</div>
      </infinite-loading>
      </div>
      <!-- spinner : default, spiral, circles, bubbles, waveDots -->
    </div>
  </div>
</template>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.10/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.27.0/moment.min.js"></script>
<script>
import NavBar from "../../components/NavigationBar.vue";
import store from "@/vuex/store.js";
import http from "@/util/http-common";
import FeedModal from "../feed/FeedModal.vue";
import InfiniteLoading from "vue-infinite-loading";
import StarRating from 'vue-star-rating'

export default {
  name: "FeedWrite",
  components: {
    FeedModal,
    NavBar,
    InfiniteLoading,
    StarRating
  },
  data: () => {
    return {
      openWrite: "",
      feeds: [],
      nickname: "",
      userId: "",
      click: true,
      content: "",
      file: "",
      preview: "",
      user: store.state.userInfo.data,
      limit: 0,
      rating : 0,
      plusContent : true,
      index : 0,
      loaded: false,
      loading: false
    };
  },
  mounted() {
    this.loaded = false;
    this.loading = false;
    this.userId = store.state.userInfo.data.id;
    this.nickname = store.state.userInfo.data.nickname;
    this.isLogin = store.state.isLogin;
    this.$EventBus.$on('updateLike', (data) => {
      if(this.feeds[data]){
        this.feeds[data].isClick = !this.feeds[data].isClick;
        this.index = data;
      }
      })
      this.$EventBus.$on('updateLikes', (data) => {
        this.feeds[this.index].likes = data;
    })
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
    setTimeout(() => {
      this.timeLoading();
    }, 700);
  },
  methods: {
    timeLoading(){
      this.loaded=true;
    },
    setRating(rating){
      this.rating = rating;
    },
    addLike(isClick, feedId, index) {
      //좋아요 버튼 클릭시 실행 함수
      if (this.click) {
        this.click = !this.click;
        http
          .put(`feeds/like`, {
            feedId: feedId,
            userId: this.userId,
            isClick: isClick,
            size: this.feeds.length,
            type: "write",
            word:"",
          })
          .then((data) => {
            this.feeds[index].isClick = !this.feeds[index].isClick;
            this.feeds[index].likes = data.data.likes;
            this.click = true;
          });
      } else {
        //alert("");
      }
    },
    openReply(feed, index) {
      //댓글 버튼 클릭시 실행 함수
      store.dispatch("openReply", feed);
      store.dispatch("openReplyIndex", index);
      this.$bvModal.show("bv-modal-feed");
    },
    modifyContent(feed){
      if (feed.modify == 0){
        alert("이미 수정한 피드입니다!");
      }else {
        http.put(`feeds/`+feed.id)
        .then((data) => {
          this.$router.go("../feed/write");
        })
        .catch(err => {
          alert("오류가 발생하였습니다.");
        })
      }
    },
    addReply(feedId, feedData, index) {
      let comment = {
        userId: this.userId,
        feedId: feedId,
        content: feedData,
        nickname: this.nickname,
      };
      http
        .post(`comments/`, comment)
        .then((data) => {
          alert("댓글등록 완료");
          //this.$router.go(0);
        })
        .catch((err) => {});
      
      this.feeds[index].data = "";
    },
    feedWrite(feed) {
      //id를 받아서 펼치게 될 경우를 정해준다.
      this.openWrite = feed.id;
    },
    addReview(feedId, reviewData) {
      let formData = new FormData();
      formData.append("file", this.file);
      formData.append("feedId", feedId);
      formData.append("imageUrl", this.preview);
      formData.append("content", reviewData);
      formData.append("star", this.rating);
      http
        .put(`feeds/`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((data) => {
          alert("리뷰등록 완료");
          this.$router.go(0);
        })
        .catch((err) => {});
    },
    upload(e) {
      let file = e.target.files[0];
      this.file = file;

      this.preview = URL.createObjectURL(file);
      let reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = (e) => {
        this.preview = e.target.result;
      };
    },
    infiniteHandler($state) {
      http
        .get(`feeds/write`, {
          params: {
            userId: this.userId,
            limit: this.limit,
            word:"",
          },
        })
        .then((response) => {
          setTimeout(() => {
            if (response.data.length) {
              this.feeds = this.feeds.concat(response.data);
              this.limit += 5;
              $state.loaded();
              this.loading = true;
            } else {
              $state.complete();
            }
          }, 500);
        })
        .catch((error) => {});
    },
    formatDate(date) { 
      var d = new Date(date), 
      month = '' + (d.getMonth() + 1), day = '' + d.getDate(), year = d.getFullYear(); 
      if (month.length < 2) month = '0' + month; 
      if (day.length < 2) day = '0' + day; 
      return [year, month, day].join('-');
    }
  },
};
</script>