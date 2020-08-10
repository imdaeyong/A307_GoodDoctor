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
    <div v-else>
      <div class="mt-5">
        <h1 class="d-flex justify-content-center mb-4">병원 검색 페이지</h1>
        <page-link-search />
        <b-container fluid class="bv-example-row bv-example-row-flex-cols" style="width:72%">
          <b-row align-v="stretch">
            <b-col cols="7" class="border-right">
              <SearchHospital />
            </b-col>
            <b-col cols="5">
              <SearchHospitalMap />
            </b-col>
          </b-row>
        </b-container>
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
  mounted() {
    if (!store.state.isLogin) this.$bvModal.show("bv-modal-example");
  },
};
</script>