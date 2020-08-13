<template>
  <div>
    <NavBar />

    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>
      <User />
    </b-modal>

    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>
    <div>
      <div v-if="feeds.length == 0">
        <div class="feed-wrap">
          <div style="text-align:center">
            <h3>
              <b-icon-emoji-frown animation="throb"></b-icon-emoji-frown>QR코드를 안찍어서
              <br />작성할 수 있는 피드가 없어요
            </h3>
          </div>
        </div>
      </div>
      <!-- 기존에 내가 작성한 Feed목록 -->
      <div v-else>
        <div v-for="feed in feeds" v-bind:key="feed.id">
          <div v-if="feed.isNew">
            <div class="feed-wrap">
              <div class="feed-top">
                <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                <img src="../../assets/images/profile_default.png" alt v-else />
                <div class="user-info">
                  {{feed.user.nickname}}
                  <span style="color : red; font-weight : bold">NEW !!!NEW !!!</span>
                </div>
                <div class="user-hospital">
                  {{feed.hospital.name}}
                  <span>{{feed.updateDate}}</span>
                </div>
              </div>

              <div class="review-write-form">
                <div v-if="openWrite == feed.id" class="review-write">
                  <input
                    type="file"
                    id="file"
                    ref="file"
                    v-on:change="upload"
                    class="review-img-upload"
                  />
                  <img :src="preview" />
                  <star-rating :inline="true" text-class="rating-text" style="float : right; height : 30px; margin-right : 1em" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :increment="0.5" :star-size="20" @rating-selected="setRating" >
                  </star-rating>
                  <textarea name id cols="60%" rows="3" v-model="feed.review"></textarea>
                  <br />
                  <button v-on:click="addReview(feed.id, feed.review)">작성</button>
                </div>
              </div>
            </div>
            <div class="review-write-btn">
              <button v-if="openWrite != feed.id" v-on:click="feedWrite(feed.id)">작성하기</button>
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
                  <span>{{feed.updateDate}}</span>
                </div>
              </div>
              <div class="feed-card">
                <img :src="feed.imageUrl" />
                <div>
                  <a href>#진료잘봄#호감</a>
                  <br />
                  {{feed.content}}
                  <span>더보기...</span>
                </div>
              </div>
              <div class="feed-foot">
                <div class="feed-btn-list">
                  <div class="like" @click="addLike(feed.isClick, feed.id)">
                    <button id="heart" style="outline : 0;">
                      <b-icon-heart v-show="!feed.isClick"></b-icon-heart>
                      <b-icon-heart-fill class="f-heart" v-show="feed.isClick" style="color : red;"></b-icon-heart-fill>
                    </button>
                  </div>
                  <div class="reply">
                    <button>
                      <b-icon-chat-square v-on:click="openReply(feed)"></b-icon-chat-square>
                    </button>
                  </div>
                  <star-rating :inline="true" style="float : right; height : 30px; font-size:1em" text-class="rating-text-write" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :star-size="20" :show-rating="true" :read-only="true" :increment="0.5" :rating="feed.star">
                  </star-rating>
                  <div class="share">
                    <button>
                      <b-icon-reply v-on:click="addShare()"></b-icon-reply>
                    </button>
                  </div>
                  <span v-if="feed.likes != 0">{{feed.likes}}명이 이 게시글을 좋아합니다.</span>
                </div>
                <div class="reply-list">
                  <img :src="user.imageUrl" v-if="user.imageUrl != null" class="profile-image" />
                  <img src="../../assets/images/profile_default.png" alt v-else />
                  <div class="user-info">
                    <span v-if="isLogin">{{nickname}}</span>
                    <span v-else>닉네임</span>
                  </div>
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

<script>
import NavBar from "../../components/NavigationBar.vue";
import User from "../../views/accounts/Login.vue";
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
    User,
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
      rating : 0
    };
  },
  mounted() {
    this.userId = store.state.userInfo.data.id;
    this.nickname = store.state.userInfo.data.nickname;
    this.isLogin = store.state.isLogin;
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
  },
  methods: {
    setRating(rating){
      console.log(rating);
      this.rating = rating;
      console.log(this.rating);
    },
    addLike(isClick, feedId) {
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
            this.feeds = data.data;
            this.click = true;
          });
      } else {
        //alert("");
      }
    },
    openReply(feed) {
      //댓글 버튼 클릭시 실행 함수
      store.dispatch("openReply", feed);
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
          alert("이미지업로드 완료");
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
  },
};
</script>