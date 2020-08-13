<template>
  <div>
    <NavBar/>
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>
    
    <b-modal id="bv-modal-feed" size="xl" hide-footer hide-header>
      <FeedModal />
    </b-modal>

    <div class="HospitalDetail mx-auto">
      <h1 class="d-flex justify-content-center mt-5 mb-4">
        <span style="color: #17a2b8">{{hospital.name}}</span> 정보에요.
      </h1>
      <!-- <p>{{hospitalFeeds[0]}}</p> -->
    </div>
      
      <!-- 3D carousel -->
      <div v-if="hospitalFeeds.length==0">
        <h3 class="mt-5" style="text-align: center;">🤣아직 작성된 리뷰가 없어요ㅜㅜ</h3>
      </div>

      <div v-else> 
        <h3 class="mt-5" style="text-align: center;">선택한 병원의 리뷰는 <span style="color: #17a2b8">{{hospitalFeeds.length}}개</span>가 있네요.</h3>
        <div id="example" style="height: 22em; width: 100%;">
          <carousel-3d
            :controls-visible="true" 
            :controls-prev-html="'&#10092;'" 
            :controls-next-html="'&#10093;'"                     
            :controls-width="60" 
            :controls-height="0" 
            :clickable="true" style="height: 25em; width: 100%;">
            <slide 
              v-for="(slide, i) in slides" 
              :index="i" 
              :key="slide" 
              class="slide" 
              style="height: 340px; width : 500px; margin-left: -75px; background-color: white"
              type="button"
              >
              <figure style="background-color: ivory" @click="openReply(hospitalFeeds[i])">
                <div style="padding-top: 1em; margin-left: 1em;">
                  <div class='row'> 
                    <h5 style="margin-left: 1em">작성자: {{hospitalFeeds[i].user.nickname}}</h5>
                    <p style="font-size: .7em; margin-left: auto; margin-right: 2em">작성시간: {{hospitalFeeds[i].user.createDate}}</p>  
                  </div>
                  <hr>
                  <p>내용: {{hospitalFeeds[i].content}}</p>

                </div>
              </figure>
            </slide>
          </carousel-3d>
        </div>
      </div>

      

    <div div class="HospitalDetail mx-auto">
      <!-- API 정보 -->
      <div class="ml-3" style="text-align: center; margin-top: 100px;">
        <h3>위치 & 정보</h3>
        <p>※주의사항 : 방문 전, 전화로 확인 후 이용해주세요.</p>
      </div>
      <b-container class="bv-example-row" style="float: left;">
        <b-row>
          <b-col cols="8" style="border: 1px solid grey; text-align: left;">
            <h5 class="mt-3">🏣 위치: {{hospital.address}}</h5>
            <h5>📞 전화번호: {{hospital.phone}}</h5>
            <h5>💻 병원 URL: <span v-if="hospital.url == 0">없음</span><span v-else>{{hospital.url}}</span></h5>
            <h5>🌞 주간응급실: <span v-if="hospitalDatas.emergencyDay">운영중</span><span v-else>운영 X</span> 
                / 🌛 야간응급실: <span v-if="hospitalDatas.emergencyDay">운영중</span><span v-else>운영 X</span></h5>
            <hr>
            <h5>🥼 의사 정보</h5>
            <p>전공의: {{hospitalDatas.internist}}명</p>
            <p>인턴: {{hospitalDatas.doctor}}명</p>            
            <hr>
            <h5>📝 영업시간</h5>
            <p>일요일: {{hospitalDatas.sunStart}} ~ {{hospitalDatas.sunFinish}} </p>
            <p>월요일: {{hospitalDatas.monStart}} ~ {{hospitalDatas.monFinish}} </p>
            <p>화요일: {{hospitalDatas.tueStart}} ~ {{hospitalDatas.tueFinish}} </p>
            <p>수요일: {{hospitalDatas.wedStart}} ~ {{hospitalDatas.wedFinish}} </p>
            <p>목요일: {{hospitalDatas.thuStart}} ~ {{hospitalDatas.thuFinish}} </p>
            <p>금요일: {{hospitalDatas.friStart}} ~ {{hospitalDatas.friFinish}} </p>
            <p>토요일: {{hospitalDatas.satStart}} ~ {{hospitalDatas.monFinish}} </p>
            
            

          </b-col>
          <b-col cols="4" style="border: 1px solid grey">
            <hospitalDetailMap :hospitalInfo="hospital"></hospitalDetailMap>
          </b-col>
        </b-row>
      </b-container>

    </div>   

  </div>
</template>

<script>
import NavBar from '../NavigationBar.vue'
import User from '../../views/accounts/Login.vue'
import http from '@/util/http-common'
import store from "@/vuex/store.js"
import HospitalDetailMap from "../../components/hospital/HospitalDetailMap.vue"
import { Carousel3d, Slide } from 'vue-carousel-3d'
import FeedModal from "../feed/FeedModal.vue"

export default {
    name: "HospitalDetail",
    components: {
      NavBar,
      User,
      HospitalDetailMap,
      Carousel3d,
      Slide,
      FeedModal,
    },
    data() {
      return {
        hospitalDatas: [],
        hospital:this.$store.getters.hospital,
        hospitalFeeds: [],
        slides: 7
      } 
    },
    mounted() {
      http.get(`/hospitals/${this.hospital.id}`)
      .then(res => {
        this.hospitalDatas = res.data
      })
      ,
      http.get(`/feeds/hospital/${this.hospital.id}`)
      .then(res => {
        this.hospitalFeeds = res.data
        this.slides = this.hospitalFeeds.length
      })
    },
    methods: {
      openReply(feed) {
        //댓글 버튼 클릭시 실행 함수
        store.dispatch("openReply", feed);
        this.$bvModal.show("bv-modal-feed");
      },
    }
    
}
</script>

<style scoped>
h5 {
  margin-bottom: .6em;
}
p {
  margin-bottom: 6px;
}
</style>
