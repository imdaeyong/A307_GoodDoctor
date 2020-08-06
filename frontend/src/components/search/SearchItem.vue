<template>
  <div>

      <div v-if="hospitals.length == 0">
          <h1> 검색 결과가 없습니다 !! </h1>
      </div>

      <div v-else>
          <h1>검색 키워드 : {{this.$route.query.word}}</h1>
          <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body class="overflow-hidden my-3 ml-4">
        <b-row no-gutters>
          <b-col md="3">
            <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
          </b-col>
          <b-col md="9">
            <b-card-body class="py-2">
              <b-card-title>{{hospital.name}}</b-card-title>
              <b-card-text class="mb-1">주소 : {{hospital.address}}</b-card-text>
              <b-card-text class="mb-1">전화번호 : {{hospital.phone}}</b-card-text>
              <b-card-text class="mb-1">진료과목 : {{hospital.subject}}</b-card-text>
            </b-card-body>
          </b-col>
        </b-row>
      </b-card>
    </div>
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
      pageOffet: 0
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
    initComponent() {
      http
        .get('search/pagelink', {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            word : this.$route.query.word,
          }
        })
        .then((data) => {
            this.hospitals = data
        })
        .catch((error) => {
            alert('에러가 발생했습니다.');
        });
    }
  },
}
</script>
