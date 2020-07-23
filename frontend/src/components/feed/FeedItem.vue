<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>이 글은 아주 좋습니다.</p>
      </div>
    </div>
    <div class="feed-card">
      <div class="img" :style="{'background-image': 'url('+defaultImage+')'}"></div>
      <div class="contentsWrap">
        <h4 class="title">사용자경험(UX)을 이해하는 팀원이 되기 위하여 - 사용자에게 '기본적인' UX를 선사하기 위해 우리 모두 알아야할 사실들</h4>
        <div class="wrap">
          <div class="url">
            <a href="https://brunch.co.kr/@@63JW/25">https://brunch.co.kr/@@63JW/25</a>
          </div>
          <p class="date">2020.06.18</p>
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
