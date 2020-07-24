<template>
  <div class="feed-item">
    <div class="feed-wrap">
      <div class="feed-top">
        <img src= "../../assets/images/profile_default.png" alt="">
        <div class="user-info">닉네임</div>
        <div class="user-hospital">병원이름 <span>2020.07.13</span></div>
        <div v-if="this.$session.exists()">로그인됨</div>
      </div>
      <div class="feed-card">
        <img src= "../../assets/images/feed-sample.jpg" alt="">
        <a href="">#새로나온차#미래내차</a><br>
        이번에 현대 자동차에서 새롭게 선보이게 된 자동차인<br>
        제네시스 G80이다.<br>
        <span>더보기...</span>
      </div>
      <div class="feed-foot">
        <div class="feed-btn-list">
          <div class ="like"><b-icon-heart></b-icon-heart></div>
          <div class ="reply"><b-icon-chat-square></b-icon-chat-square></div>
          <div class ="share"><b-icon-reply></b-icon-reply></div>
        </div>
        <div class ="reply-list">
          <img src= "../../assets/images/profile_default.png" alt="">
          <div class="user-info">닉네임</div>
          <input type="text" class="reply-content" placeholder="댓글달기...">
          <button class="reply-submit">게시</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
export default {
  data: () => {
    return { defaultImage, defaultProfile };
  }

};
</script>
