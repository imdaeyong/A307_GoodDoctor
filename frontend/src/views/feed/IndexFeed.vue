<template>
  <div>
    <NavBar />
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
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop>
      <User/>
    </b-modal>
  </div>
</template>

<script>
import { mapState } from "vuex";
import FeedItem from "../../components/feed/FeedItem.vue";
import NavBar from "../../components/NavigationBar.vue";
import User from "../accounts/Login.vue";
import "../../assets/css/main.css";
export default {
  props: ["keyword"],

  components: { FeedItem, NavBar, User },
  methods: {
    onChangePWD(){
      this.$router.push("/changePassword");
    }
  },
  mounted(){
    this.$bvModal.show('bv-modal-example');
  },
  data () {
    return {
      modal: false
    }
  }
};
</script>

