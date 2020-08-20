<template>
  <div>
    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>

    <h1 class="mt-4" style="text-align:center">실속있는 내 리뷰</h1>
    <div style="margin-top : 40px; width: 100%">
      <!-- 기존에 내가 작성한 Feed목록 -->
      <div>
        <div v-for="(feed, index) in feeds" v-bind:key="feed.id">
          <div v-if="!feed.isNew">
            <div class="myreview-feed">
              <div class="feed-top">
                <img v-if="user.imageUrl != null" :src="user.imageUrl"  class="profile-image" />
                <img v-else src="../../../assets/images/profile_default.png"/>
                <div class="user-info">{{feed.user.nickname}}</div>
                <div class="user-hospital">
                  {{feed.hospital.name}}
                  <span>{{formatDate(feed.updateDate)}}</span>
                </div>
              </div>
              <div class="feed-card">
                <img v-if="feed.imageUrl != null" :src="feed.imageUrl"/>
                
                <div class="feed-content">
                  <a href>#진료잘봄 #호감</a>
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
                  <star-rating :inline="true" style="float : right; height : 30px; font-size:1em" text-class="rating-text-write" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :star-size="20" :show-rating="true" :read-only="true" :increment="0.5" :rating="feed.star">
                  </star-rating>
                  <div class="share">
                    <button>
                    </button>
                  </div>
                  <span v-show="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span>
                </div>
                <div class="reply-list">
                  <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                  <img src="../../../assets/images/profile_default.png" alt v-else />
                  <input
                    type="text"
                    class="reply-content"
                    placeholder="댓글달기..."
                    v-model="feed.data"
                  />
                  <button class="reply-submit" v-on:click="addReply(feed.id, feed.data)">게시</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <infinite-loading @infinite="infiniteHandler" spinner="bubbles">
        <div
          slot="no-more"
          style="color: rgb(102, 102, 102); font-size: 20px; padding: 10px 0px;"
        >목록의 끝입니다 :)</div>
      </infinite-loading>
      <!-- spinner : default, spiral, circles, bubbles, waveDots -->
    </div>
  </div>
</template>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.10/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.27.0/moment.min.js"></script>

<script>
import FeedModal from "../../feed/FeedModal.vue";
import store from "@/vuex/store.js";
import http from "@/util/http-common";
import InfiniteLoading from "vue-infinite-loading";
import StarRating from 'vue-star-rating'

export default {
  name: "myReview",
  components: {
    InfiniteLoading,
    StarRating,
    FeedModal,
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
      index : 0,
    };
  },
  created() {
      this.$EventBus.$on('updateLike', (data) => {
        this.feeds[data].isClick = !this.feeds[data].isClick;
        this.index = data;
      })
      this.$EventBus.$on('updateLikes', (data) => {
        this.feeds[this.index].likes = data;
      })
  },
  mounted() {
    this.userId = store.state.userInfo.data.id;
    this.nickname = store.state.userInfo.data.nickname;
    this.isLogin = store.state.isLogin;
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
  },
  methods: {
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
    addReply(feedId, feedData) {
      let comment = {
        userId: this.userId,
        feedId: feedId,
        content: feedData,
      };
      http
        .post(`comments/`, comment)
        .then((data) => {
          alert("댓글등록 완료");
        })
        .catch((err) => {});
    },
    feedWrite(id) {
      //id를 받아서 펼치게 될 경우를 정해준다.
      this.openWrite = id;
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
            } else {
              $state.complete();
            }
          }, 800);
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