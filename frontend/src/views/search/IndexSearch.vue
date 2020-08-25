<template>
  <div>
    <NavBar />
    <div style="text-align:center" v-if="!this.loaded"><img src = "../../assets/images/bonoloading.gif"/> </div>
    <div v-if ="this.loaded">
      <div v-if="!this.$route.query.no">
        <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
          <FeedModal />
        </b-modal>
          <search-feed />
      </div>
      <div v-if="this.$route.query.no" class="mt-5">
        <b-container fluid class="bv-example-row bv-example-row-flex-cols" style="width:72%">
          <b-row align-v="stretch">
            <b-col cols="6" class="border-right">
              <SearchHospital />
            </b-col>
            <b-col cols="5">
              <SearchHospitalMap />
            </b-col>
          </b-row>
        </b-container>
        <page-link-search />
      </div>
      
    </div>
  </div>
</template>

<script>
import NavBar from "../../components/NavigationBar.vue";
import SearchHospital from "../../components/search/SearchHospital.vue";
import SearchFeed from "../../components/search/SearchFeed.vue";
import SearchHospitalMap from "../../components/search/SearchHospitalMap.vue";
import PageLinkSearch from "../../components/PageLinkSearch.vue";
import store from "../../vuex/store.js";
import FeedModal from "../../components/feed/FeedModal.vue";

export default {
  components: {
    NavBar,
    SearchHospital,
    SearchHospitalMap,
    PageLinkSearch,
    SearchFeed,
    FeedModal,
  },
  data: () => {
    return {
      loaded:false,
    };
  },
  mounted() {
    this.loaded=false;
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
    setTimeout(() => {
      this.timeLoading();
    }, 600);
  },
  methods: {
    timeLoading(){
      this.loaded=true;
    },
    onChangePWD() {
      this.$router.push("accounts/changePassword");
    },
    
  },
   
};
</script>