<template>
  <div>
    <NavBar />
    <h1 class="mt-4">지금 관심있는 병원 정보</h1>
    <div v-for="hospital in favorites" v-bind:key="hospital.id">

     <b-card no-body @click="hospitalDataSend(hospital)"
       class="overflow-hidden my-3 btn-left"
        style="height: 10rem; padding : 1em;">
        <b-row >
          <b-col md="3">
            <img src="../../../assets/images/hospital/default-doctor.png" alt="Image" class="hospital-doctor">
          </b-col>
          <b-col md="8">
            <div class="hospital-info">
              <div class="mt-1"><span>{{hospital.name}}</span></div>
              <div class="mt-2">평점| 리뷰수</div>
              <div class="mt-2">진료과목 : {{hospital.subject}}</div>
            </div >
          </b-col>
        </b-row>
      </b-card>
    </div>


  </div>
</template>

<script>
import http from "@/util/http-common";
import NavBar from "../../NavigationBar.vue";
import '../../../assets/css/hospital.scss'

export default {
  name: "myHospital",
  components: {
    NavBar,
  },
  data: () => {
    return {
      favorites: [],
    };
  },
  created() {
    var userId = this.$store.getters.userInfo.data.id;
    this.favorites = JSON.parse(localStorage.getItem(userId));
    console.log(this.favorites[0]);
  },
  methods:{
    hospitalDataSend(hospital) {
      this.$store.commit('mutateHospital',hospital)
      this.$router.push({name: "HospitalDetail"})
    },
  }
};
</script>