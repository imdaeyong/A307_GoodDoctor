<template>
  <div>
    <NavBar />
    <FeedItem />
    <!--
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop>
      <User/>
    </b-modal>
    -->
    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>
    
  </div>
</template>

<script>
import { mapState } from "vuex";
import FeedItem from "../../components/feed/FeedItem.vue";
import FeedModal from "../../components/feed/FeedModal.vue";
import NavBar from "../../components/NavigationBar.vue";
//import User from "../accounts/Login.vue";
import "../../assets/css/main.css";
import "../../assets/css/feed.scss";
import "../../assets/css/feedModal.scss";
export default {
  props: ["keyword"],

  components: { FeedItem, FeedModal, NavBar, //User 
  },
  methods: {
    onChangePWD(){
      this.$router.push("/changePassword");
    }
  },
  mounted(){
    //if(this.$session.exists()) alert("오 로그인있음");
    //this.$bvModal.show('bv-modal-example');
    
  },
  data () {
    return {
      modal: false
    }
  }
};
</script>

