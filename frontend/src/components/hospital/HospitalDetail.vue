<template>
  <div>
    <NavBar/>
    
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
        <div id="example" style="height: 24em; width: 100%;">
          <carousel-3d
            :controls-visible="true" 
            :controls-prev-html="'&#10092;'" 
            :controls-next-html="'&#10093;'"                     
            :controls-width="60" 
            :controls-height="0" 
            :clickable="true" style="height: 30em; width: 90%;">
            <slide 
              v-for="(slide, i) in slides" 
              :index="i" 
              :key="slide" 
              class="slide" 
              style="height: 470px; width : 380px; margin-left: -10px; background-color: white"
              type="button"
              >
              <div @click="openReply(hospitalFeeds[i])">
              <figure style="" >
                <div style="padding-top: 1em; margin-left: 1em;">
                  <div class='row'> 
                    <div style="margin-left: 1em">
                      <img :src="hospitalFeeds[i].user.imageUrl" v-if="hospitalFeeds[i].user.imageUrl != null" class="profile-image" style="width : 1.5em;"/>
                      <img src="../../assets/images/profile_default.png" style="width : 1.5em; height: 1.5em; float: left; margin-right:1em" alt v-else />
                      {{hospitalFeeds[i].user.nickname}}
                    </div>
                    <p style="font-size: .7em; margin-left: auto; margin-right: 2em">{{formatDate(hospitalFeeds[i].updateDate)}}</p>  
                  </div>
                  <hr>
                  <div style="padding-right : 16px;" v-if="hospitalFeeds[i].imageUrl != null">
                    <img :src="hospitalFeeds[i].imageUrl"   height="250px;"/>
                    <hr>
                  </div>
                  
                  <div style="padding : 0px;" v-if="plusContent">
                    <div class="text-truncate" style="width: 60%; padding : 0px;">
                      {{hospitalFeeds[i].content}}
                    </div>
                  </div>

                </div>
              </figure>
              </div>
            </slide>
          </carousel-3d>
        </div>
      </div>
    <div div class="HospitalDetail mx-auto">
      <!-- API 정보 -->
      <div class="ml-3" style="text-align: center; margin-top: 150px;">
        <h3>위치 & 정보</h3>
        <p>※주의사항 : 방문 전, 전화로 확인 후 이용해주세요. </p>
        <button v-if="isFavorite" style="outline: none; padding-left: 5em; float : right; margin-top : -3em;" @click.stop="addFavorites(hospital)" z-index=5 width=40px;>
          <span style="color : #17a2b8; font-size : 1.1em; font-weight : 700;">즐겨찾기 제거 </span>
          <img src="../../assets/images/hospital/fav.png" width= 31em; alt="favorites_Button">
        </button>

        <button v-else style="outline: none;  padding-left: 5em; float : right; margin-top : -3em;" @click.stop="addFavorites(hospital)" z-index=5 width=40px;>
          <span style="color : #17a2b8; font-size : 1.1em; font-weight : 700;">즐겨찾기 추가 </span>
          <img src="../../assets/images/hospital/unfav.png" width= 31em; alt="favorites_Button">
        </button>
      </div>
      <b-container class="bv-example-row" style="float: left;">
        <b-row style="margin-bottom: 3em;">
          <b-col cols="8" style="border: 1px solid grey; text-align: left;">
            <h5 class="mt-3">🏣 위치: {{hospital.address}}</h5>
            <h5>📞 전화번호: {{hospital.phone}}</h5>
            <h5>💻 병원 URL: <span v-if="hospital.url == 0">없음</span><span v-else><a :href= "hospital.url">{{hospital.url}}</a></span></h5>
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
import http from '@/util/http-common'
import store from "@/vuex/store.js"
import HospitalDetailMap from "../../components/hospital/HospitalDetailMap.vue"
import { Carousel3d, Slide } from 'vue-carousel-3d'
import FeedModal from "../feed/FeedModal.vue"
import '../../assets/css/hospital.scss'

export default {
    name: "HospitalDetail",
    components: {
      NavBar,
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
        slides: 7,
        user: store.state.userInfo.data,
        plusContent : true,
        isFavorite:false,
      } 
    },
 mounted() {
      http.get(`/hospitals/${this.hospital.id}`)
      .then(res => {
        this.hospitalDatas = res.data
      })
      ,
      http.get(`/feeds/hospital`, {
        params: {
          userId: this.user.id,
          id: this.hospital.id
        }
      })
      .then(res => {
        this.hospitalFeeds = res.data
        this.slides = this.hospitalFeeds.length
      })

      var userId = this.$store.getters.userInfo.data.id
      var favorites= []
      if(localStorage.getItem(userId)){
        favorites = JSON.parse(localStorage[userId]);
        for(var i=0; i<favorites.length;i++){
          if(favorites[i].id==this.hospital.id){
            this.isFavorite=true;
            break;
          }
        }
      }
    },
    methods: {
      openReply(feed) {
        //댓글 버튼 클릭시 실행 함수
        store.dispatch("openReply", feed);
        this.$bvModal.show("bv-modal-feed");
      },
      addFavorites(hospital,event){
      // event.stopPropagation()
      var userId = this.$store.getters.userInfo.data.id
      var favorites= []
      var isExist=false;
      
      if(localStorage.getItem(userId)){//즐겨찾기가 있을때
        favorites = JSON.parse(localStorage[userId]);
        for(var i=0; i<favorites.length;i++){
          if(favorites[i].id==hospital.id){
            favorites.splice(favorites.indexOf(favorites[i]),1);
            this.isFavorite=false;
            isExist=true;
            break;
          }
        }
      }
      if(!isExist){
        favorites.push(hospital)
        this.isFavorite=true;
      }
      if(favorites.length==0){
        localStorage.removeItem(userId);
      }else{
        localStorage.setItem(userId,JSON.stringify(favorites));
      }
    },
    formatDate(date) { 
      var d = new Date(date), 
      month = '' + (d.getMonth() + 1), day = '' + d.getDate(), year = d.getFullYear(); 
      if (month.length < 2) month = '0' + month; 
      if (day.length < 2) day = '0' + day; 
      return [year, month, day].join('-');
    }
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