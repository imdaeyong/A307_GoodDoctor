<template>
  <div>
    <NavBar />
    <h1 class="mt-4">지금 관심있는 병원 정보</h1>

    <div class="myreview-feed">
      <div v-for="hospital in favorites" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital)"
        class="overflow-hidden my-3 btn-left"
          style="height: 10rem; padding : 1em;">
          <b-container fluid class="bv-example-row">
            <b-row  align-h="center">            
              <b-col md="3">
                <div><img src="../../../assets/images/hospital/default-doctor.png" alt="Image" class="hospital-doctor"></div>
              </b-col>
              <b-col md="9" style="padding-left: 1em">
                <div class="hospital-info">
                  <div class="mt-1"><span>{{hospital.name}}</span></div>
                  <div class="mt-2"><p style="float : left; margin-right : 10px;">평균 평점</p>
                  <star-rating :inline="true" text-class="rating-text" style="float : left; margin-top : -7px; height : 30px; font-size:0.8em;" border-color="#d8d8d8" :rounded-corners="true" :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]" :star-size="20" :show-rating="true" :read-only="true" :increment="0.5" :rating="hospital.avgStar">
                </star-rating><br>
                </div>
                <div class="mt-2">리뷰수 : {{hospital.reviewCnt}}</div>
                  <div class="mt-2">진료과목  : {{hospital.subject}}</div>
                </div>
              </b-col>            
            </b-row>
          </b-container>
        </b-card>
      </div>
    </div>


  </div>
</template>

<script>
import http from "@/util/http-common";
import NavBar from "../../NavigationBar.vue";
import StarRating from 'vue-star-rating'
import '../../../assets/css/hospital.scss'

export default {
  name: "myHospital",
  components: {
    NavBar,
    StarRating
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