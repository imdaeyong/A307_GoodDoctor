<template>
  <div>
    <div v-if="hospitals.length == 0">
      <h1>검색 결과가 없습니다 !!</h1>
    </div>
    <div v-else>
      <h1>검색 키워드 : {{this.$route.query.word}}</h1>
      <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
        <b-card no-body class="overflow-hidden my-3 ml-4">
          <b-row no-gutters>
            <b-col md="3">
              <b-card-img
                src="https://picsum.photos/400/400/?image=20"
                alt="Image"
                class="rounded-0"
              ></b-card-img>
            </b-col>
            <b-col md="9">
              <b-card-body class="py-2">
                <b-card-title class="mt-2">{{hospital.name}}</b-card-title>
                <b-card-text class="mt-2">평점| 리뷰수</b-card-text>
                <b-card-text class="mt-2">진료과목 : {{hospital.subject}}</b-card-text>
              </b-card-body>
            </b-col>
          </b-row>
        </b-card>
        <button @click="hospitalZoom(hospital)">지도에서 보기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import http from "@/util/http-common";
export default {
  data: () => {
    return {
      hospitals: [],
      pageLimit: 10,
      pageOffet: 0,
      seletDatsId: "",
    };
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
    hospitalDataSend(id) {
      this.$router.push({ name: "HospitalDetail", params: { id: id } });
    },
    hospitalZoom(id) {
      this.$store.commit("addHospitalZoom", id);
    },

    initComponent() {
      http
        .get("/hospitals", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            word: this.$route.query.word,
            sido: "",
            gu: "",
            subject: "",
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
