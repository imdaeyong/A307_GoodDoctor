<template>
  <div>
    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital.id)" class="overflow-hidden my-3 ml-4 btn-left">

        <b-row no-gutters>
          <b-col md="3">
            <b-card-img src="https://picsum.photos/400/400/?image=114" alt="Image" class="rounded-0"></b-card-img>
          </b-col>
          <b-col md="9">
            <b-card-body class="py-2">
              <b-card-title class="mt-2">{{hospital.name}}</b-card-title>
              <b-card-text class="mt-2">평점| 리뷰수</b-card-text>
              <b-card-text class="mt-2">진료과목 : {{hospital.subject}}</b-card-text>
              <button @click="hospitalDetail(hospital.id)">상세보기</button>
              <button @click="hospitalZoom(hospital)">지도에서 보기</button>
            </b-card-body>
          </b-col>
        </b-row>
        
      </b-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import http from '@/util/http-common'

export default {
  name : 'HospitalItem',
  data: () => {
    return {
      hospitals: [], 
      pageLimit: 10, 
      pageOffet: 0,
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
    hospitalZoom(id){
      this.$store.commit('addHospitalZoom',id);
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
    },
    
  },
}
</script>
