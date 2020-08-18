<template>
  <div class="feed-item">
    <div class="feed-wrap" v-if="coronaInfo != {}">
      <div class="corona-title"><span>코로나 바이러스</span>(COVID-19) 국내현황</div>
      <table class="corona-table">
        <tr>
          <th>{{coronaInfo[0]}}</th>
          <th>{{coronaInfo[3]}}</th>
          <th>{{coronaInfo[6]}}</th>
          <th>{{coronaInfo[9]}}</th>
        </tr>
        <tr>
          <td>{{coronaInfo[1]}}</td>
          <td>{{coronaInfo[4]}}</td>
          <td>{{coronaInfo[7]}}</td>
          <td>{{coronaInfo[10]}}</td>
        </tr>
        <tr>
          <td>{{coronaInfo[2]}} <b-icon-caret-up-fill v-if="coronaInfo[2] != '-'"></b-icon-caret-up-fill></td>
          <td>{{coronaInfo[5]}} <b-icon-caret-up-fill v-if="coronaInfo[5] != '-'"></b-icon-caret-up-fill></td>
          <td>{{coronaInfo[8]}} <b-icon-caret-up-fill v-if="coronaInfo[8] != '-'"></b-icon-caret-up-fill></td>
          <td>{{coronaInfo[11]}} <b-icon-caret-up-fill v-if="coronaInfo[11] != '-'"></b-icon-caret-up-fill></td>
        </tr>
      </table>
    </div>
    <div v-for="(feed, index) in feeds" v-bind:key="feed.id" v-bind:index="index">
      <div v-if="!feed.isNew">
        <div class="feed-wrap" v-bind:data="feed.data">
          <div class="feed-top">
            <img :src="feed.user.imageUrl" v-if="feed.user.imageUrl != null" class="profile-image" />
            <img src="../../assets/images/profile_default.png" alt v-else />
            <div class="user-info">{{feed.user.nickname}}</div>
            <div class="user-hospital">
              {{feed.hospital.name}}
              <span>{{formatDate(feed.updateDate) }}</span>
            </div>
          </div>
          <div class="feed-card">
            <img :src="feed.imageUrl"  v-if="feed.imageUrl != null"/>
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
                  <b-icon-chat-square @click="openReply(feed, index)"></b-icon-chat-square>
                </button>
              </div>
              <star-rating :inline="true" text-class="rating-text" style="float : right; height : 30px; font-size:0.8em;" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :star-size="20" :show-rating="true" :read-only="true" :increment="0.5" :rating="feed.star">
              </star-rating>
              <div class="share">
                <button>
                  <b-icon-reply @click="addShare()"></b-icon-reply>
                </button>
              </div>
              <span v-show="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span>
            </div>
            <div class="reply-list">
              <img
                :src="feed.user.imageUrl"
                v-if="feed.user.imageUrl != null"
                class="profile-image"
              />
              <img src="../../assets/images/profile_default.png" alt v-else />
              <div class="user-info">
                <span v-if="isLogin">{{feed.user.nickname}}</span>
                <span v-else>닉네임</span>
              </div>
              <input type="text" class="reply-content" placeholder="댓글달기..." v-model="feed.data" />
              <button class="reply-submit" @click="addReply(feed.id, feed.data)">게시</button>
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
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import store from "@/vuex/store.js";
import http from "@/util/http-common";
import InfiniteLoading from "vue-infinite-loading";
import StarRating from 'vue-star-rating'

export default {
  data: () => {
    return {
      feeds: [],
      nickname: "",
      isLogin: store.state.isLogin,
      userId: "",
      content: "",
      click: true,
      limit: 0,
      plusContent : true,
      rating : 0,
      index : 0,
      coronaInfo : {}
    };
  },
  components: {
    InfiniteLoading,
    StarRating
  },
  mounted() {
    this.userId = store.state.userInfo.data.id;
    http
      .get(`feeds/crawling`)
          .then((data) => {
            this.coronaInfo = data.data.split(" ");
            console.log(this.coronaInfo);
          });
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
  methods: {
    addLike(isClick, feedId, index) {
      //좋아요 버튼 클릭시 실행 함수
      if (this.click) {
        this.click = !this.click;
        http
          .put(`feeds/like`, {
            feedId: feedId,
            userId: this.userId,
            isClick: isClick,
            type: "main",
            word: "",
            size: this.feeds.length,
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
    addShare() {
      //공유버튼 클릭시 실행 함수
      alert("하이");
    },
    addReply(feedId, feedData) {
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
    },
    infiniteHandler($state) {
      http
        .get(`feeds/`, {
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
