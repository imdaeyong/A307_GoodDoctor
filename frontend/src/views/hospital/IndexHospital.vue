<template>
  <div>
    <NavBar/>
    <div style="text-align:center" v-show="!this.loaded"><img src = "../../assets/images/bonoloading.gif"/> </div>
    <div class="mt-5" v-show="this.loaded">
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

export default {
  name: "indexHospital",
  components: {
    NavBar,
    HospitalItem,
    HospitalMap,
    PageLink,
  },
  data: () => {
    return {
      loaded:false,
    };
   },
   mounted(){
    this.loaded=false;
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
     setTimeout(() => {
      this.timeLoading();
    }, 700);
  },
  methods: {
    timeLoading(){
      this.loaded=true;
    },
  },
};
</script>

<style>

</style>
