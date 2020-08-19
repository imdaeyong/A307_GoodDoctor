<template>
  <div>
    <div v-if="this.$route.query.subject">
      <h2>'{{this.$route.query.subject}}'에 대한 검색 결과입니다.</h2>
    </div>
    <div v-else>
      <h2>'{{this.$route.query.sido}} {{this.$route.query.gu}}'에 대한 검색 결과입니다.</h2>
    </div>
    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital)"
        @mouseover="doMouseOver(hospital)" class="overflow-hidden my-3 btn-left"
        style="height: 10rem; padding : 1em;">

        <div>
          <b-row>            
            <b-col md="3">
              <div><img src="../../assets/images/hospital/default-doctor.png" alt="Image" class="hospital-doctor"></div>
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
            <b-col md="4" style="padding-left: 1em">
            </b-col>            
          </b-row>
        </div>
      </b-card>
    </div>
  </div>
</template>


<script>
import http from "@/util/http-common";
import StarRating from 'vue-star-rating'
import '../../assets/css/hospital.scss'

export default {
  name: "HospitalItem",
  data: () => {
    return {
      hospitals: [],
      pageLimit: 10,
      seletDatsId: "",
    };
  },
  components: {
    StarRating
  },
  created() {
    this.initComponent();
  },
  watch: {
    "$route.query": function () {
      this.initComponent();
    },
  },
  methods: {
    doMouseOver(hospital) {
      this.$store.commit('addHospitalHover',hospital)
    },
    hospitalDataSend(hospital) {
      this.$store.commit('mutateHospital',hospital)
      this.$router.push({name: "HospitalDetail"})
    },
    hospitalZoom(hospital){
      this.$store.commit('addHospitalZoom',hospital)
    },

    initComponent() {
      http
        .get("hospitals/", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            subject: this.$route.query.subject,
            sido: this.$route.query.sido,
            gu: this.$route.query.gu,
            word: "",
          },
        })
        .then((data) => {
          this.hospitals = data;
        })
        .catch((error) => {
          alert("에러가 발생했습니다.");
        });
    },
  },
};
</script>