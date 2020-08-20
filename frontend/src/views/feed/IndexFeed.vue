<template>
  <div>
    <NavBar />

    <FeedItem />
    
    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>
  </div>
</template>

<script>
import "../../assets/css/main.scss";
import "../../assets/css/feed.scss";
import "../../assets/css/feedModal.scss";
import { mapState } from "vuex";
import FeedItem from "../../components/feed/FeedItem.vue";
import FeedModal from "../../components/feed/FeedModal.vue";
import NavBar from "../../components/NavigationBar.vue";
import store from '@/vuex/store.js'

export default {
  name: "IndexFeed",
  components: { 
    FeedItem, 
    FeedModal, 
    NavBar, 
  },
  methods: {
    onChangePWD(){
      this.$router.push("accounts/changePassword");
    }
  },
  mounted(){
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
  }
};
</script>
