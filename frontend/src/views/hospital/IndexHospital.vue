<template>
  <div>
    <NavBar/>
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>

    <div class="mt-5">

      <b-container fluid class="bv-example-row bv-example-row-flex-cols" style="width:72%">
        <b-row align-v="stretch">
          <b-col cols="6" class="border-right">
            <HospitalItem/>
          </b-col>
          <b-col cols="5">
            <HospitalMap/>
          </b-col>
        </b-row>
      </b-container>
      <PageLink/>
    </div>
  </div>
</template>

<script>
import NavBar from "../../components/NavigationBar.vue"
import HospitalItem from "../../components/hospital/HospitalItem.vue"
import HospitalMap from "../../components/hospital/HospitalMap.vue"
import PageLink from "@/components/PageLink.vue"
import store from '../../vuex/store.js'
import User from "../../views/accounts/Login.vue"

export default {
  name: "indexHospital",
  components: {
    NavBar,
    HospitalItem,
    HospitalMap,
    PageLink,
    User,
  },
  mounted(){
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
  },
};
</script>

<style>

</style>