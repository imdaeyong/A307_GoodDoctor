<template>
  <div>
    <NavBar />
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>
      <User />
    </b-modal>

    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>

    <div v-if="!this.$route.query.no">
      <search-feed />
    </div>
    <div v-else class="mt-5">
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
</template>

<script>
import NavBar from "../../components/NavigationBar.vue";
import SearchHospital from "../../components/search/SearchHospital.vue";
import SearchFeed from "../../components/search/SearchFeed.vue";
import SearchHospitalMap from "../../components/search/SearchHospitalMap.vue";
import PageLinkSearch from "../../components/PageLinkSearch.vue";
import store from "../../vuex/store.js";
import User from "../../views/accounts/Login.vue";
import FeedModal from "../../components/feed/FeedModal.vue";

export default {
  components: {
    NavBar,
    SearchHospital,
    SearchHospitalMap,
    PageLinkSearch,
    User,
    SearchFeed,
    FeedModal,
  },
  methods: {
    onChangePWD() {
      this.$router.push("accounts/changePassword");
    },
  },
  mounted() {
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
  },
};
</script>