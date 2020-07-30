<template>
  <div>
    <NavBar />
    <div class="user">
      <div class="wrapC mt-5">
        <h1>병원 리스트 페이지입니다.</h1>
        <page-link />
      </div>
    </div>

    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row align-v="stretch">
        <b-col cols="8" class="border-right" style="background-color: ivory">
          <HospitalItem/>
        </b-col>
        <b-col cols="4">
          <HospitalMap/>
        </b-col>
      </b-row>
    </b-container>
    
  </div>
</template>

<script>
import NavBar from "../../components/NavigationBar.vue";
import HospitalItem from "../../components/hospital/HospitalItem.vue";
import HospitalMap from "../../components/hospital/HospitalMap.vue"
import PageLink from "@/components/PageLink.vue";

export default {
  components: {
    NavBar,
    HospitalItem,
    HospitalMap,
    PageLink,
  },
};
</script>

