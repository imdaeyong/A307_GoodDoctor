<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <button
        class="btn btn--back btn--changepwd"
        @click="onChangePWD"
      >비밀번호변경</button>
      <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import FeedItem from "../../components/feed/FeedItem.vue";

export default {
  props: ["keyword"],

  components: { FeedItem },
  methods: {
    onChangePWD(){
      this.$router.push("/changePassword");
    }
  }
};
</script>

