<template>
  <div>
    <!-- <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
        {{hospital.id}} / {{hospital.name}} / {{hospital.sido}} / {{hospital.gu}} / {{hospital.phone}} / {{hospital.subject}}/ {{hospital.lat}}/ {{hospital.lng}}
    </div>-->

    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body class="overflow-hidden my-3 ml-4" style="max-width: 1000px;">
        <b-row no-gutters>
          <b-col md="2">
            <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
          </b-col>
          <b-col md="10">
            <b-card-body>
              <b-card-title>{{hospital.name}}</b-card-title>
              <b-card-text>주소 : {{hospital.address}}</b-card-text>
              <b-card-text>전화번호 : {{hospital.phone}}</b-card-text>
              <b-card-text>진료과목 : {{hospital.subject}}</b-card-text>
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
  data: () => {
    return {
      hospitals: [],
    };
  },
  mounted() {
    // console.log(this.$route.query.subject);
    console.log(this.$route.query.sido + " " + this.$route.query.gu);

    if (this.$route.query.subject == undefined) {
      // console.log(this.$route.query.sido + " " + this.$route.query.gugun);
      http
        .get(
          `city?sido=${this.$route.query.sido}&gu=${this.$route.query.gu}`
        )
        .then((response) => {
          // console.log(response);
          this.hospitals = response;
        })
        .catch((error) => {});
    } else {
      http
        .get(
          `hospitals/subject?subject=${this.$route.query.subject}`
        )
        .then((response) => {
          console.log(response);
          this.hospitals = response;
        })
        .catch((error) => {});
    }
  },
};
</script>

<style></style>