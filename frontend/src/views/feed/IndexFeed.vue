<template>
  <div>
    <NavBar />
    <FeedItem />

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
import "../../assets/css/feed.scss";
export default {
  props: ["keyword"],

  components: { FeedItem, NavBar, User },
  methods: {
    onChangePWD(){
      this.$router.push("/changePassword");
    }
  },
  mounted(){
    //if(this.$session.exists()) alert("오 로그인있음");
    this.$bvModal.show('bv-modal-example');
    
  },
  data () {
    return {
      modal: false
    }
  }
};
</script>

