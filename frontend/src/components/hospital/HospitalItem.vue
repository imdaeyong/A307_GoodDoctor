<template>
  <div>
    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital)" class="overflow-hidden my-3 ml-4 btn-left" style="z-index: 3;">
        <b-row no-gutters>
          <b-col md="3">
            <b-card-img src="https://picsum.photos/400/400/?image=42" alt="Image" class="rounded-0"></b-card-img>
          </b-col>
          <b-col md="9">
            <b-card-body class="py-2">
              <b-card-title class="mt-2">{{hospital.name}}</b-card-title>
              <b-card-text class="mt-2">평점 | 리뷰수</b-card-text>
              <div class="row mt-2" style="padding-left:15px">
                <b-card-text>진료과목 : {{hospital.subject}}</b-card-text>
                <button @click="hospitalZoom(hospital)" style="margin-left:auto; margin-right:1rem; z-index: 10; background-color: red">지도에서 보기</button>
              </div>
            </b-card-body>
          </b-col>
        </b-row>
      </b-card>
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common'

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
    hospitalDataSend(id) {
      this.$router.push({name: "HospitalDetail", params: {"id": id}})
    },
    hospitalZoom(id){
      this.$store.commit('addHospitalZoom',id)
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
