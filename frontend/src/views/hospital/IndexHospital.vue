<template>
  <div>
    <NavBar />
    <div class="mt-5">
      <h1 class="d-flex justify-content-center mb-4">병원 리스트 페이지</h1>
      <PageLink/>
      <b-container fluid class="bv-example-row bv-example-row-flex-cols" style="width:72%">
        <b-row align-v="stretch">
          <b-col cols="7" class="border-right">
            <HospitalItem/>
          </b-col>
          <b-col cols="5">
            <HospitalMap/>
          </b-col>
        </b-row>
      </b-container>
    </div>
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

<style>

</style>