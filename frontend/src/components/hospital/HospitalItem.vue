<template>
  <div>
    <h2>'{{hospitals.data[0].subject}}'에 대한 검색 결과입니다.</h2>
    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital)" 
        @mouseover="doMouseOver(hospital)" class="overflow-hidden my-3 btn-left"
        style="height: 10rem; padding : 1em;">
        <b-row >
          <b-col md="3">
            <img src="../../assets/images/hospital/default-doctor.png" alt="Image" class="hospital-doctor">
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
import http from '@/util/http-common'
import '../../assets/css/hospital.scss'

export default {
  name : 'HospitalItem',
  data: () => {
    return {
      hospitals: [], 
      pageLimit: 10, 
      seletDatsId: ""
    }
  },
  created() {
    this.initComponent();
  },
  watch: {
    '$route.query': function () {
      this.initComponent();
    }
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
      .get('hospitals/pagelink', {
        params: {
          limit: this.pageLimit,
          offset: `${this.$route.query.no - this.pageLimit}`,
          subject : this.$route.query.subject,
          sido : this.$route.query.sido,
          gu : this.$route.query.gu,
        }
      })
      .then((data) => {
          this.hospitals = data
      })
      .catch((error) => {
          alert('에러가 발생했습니다.');
      });
    }
  }
}
</script>
