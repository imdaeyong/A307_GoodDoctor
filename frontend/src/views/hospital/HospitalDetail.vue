<template>
  <div>
    <NavBar/>
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>

    <div class="HospitalDetail mx-auto">
      <h1 class="d-flex justify-content-center mt-5 mb-4">
        <span style="color : #17a2b8">{{this.$route.params.id.id}} / 병원</span> 정보에요.
      </h1>
      <p>{{this.hospitalDatas}}</p>
      
      <!-- 가로 무한 스크롤 -피드노출 -->
      <h3 >선택한 병원의 리뷰 목록</h3>
      <div class="row" style="border: 1px solid">
        <div class="box row" style="width: 255px">
          <div>
            <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="mb-2"
            >
              <b-card-text>
                Some quick example text to build on the card title and make up the bulk of the card's content.
              </b-card-text>

              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </div>
        </div>

        <div class=" row ml-4" style="width: 255px">
          <div>
            <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="mb-2"
            >
              <b-card-text>
                Some quick example text to build on the card title and make up the bulk of the card's content.
              </b-card-text>

              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </div>
        </div>

        <div class=" row ml-4" style="width: 255px">
          <div>
            <b-card
              title="Card Title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="mb-2"
            >
              <b-card-text>
                Some quick example text to build on the card title and make up the bulk of the card's content.
              </b-card-text>

              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </div>
        </div>

      </div>
      <!-- 가로 무한 스크롤 -피드노출 종료-->


      <br>
      <br>
      <br>
      <br>

      <!-- API 정보 -->
      <div>
        <h2>위치 & 정보</h2>
      </div>
      <b-container class="bv-example-row">
        <b-row>
          <b-col cols="8" style="border: 1px solid grey; text-align: left;">
            <hr>
            <h3>위치: {{this.$route.params.id.address}}</h3>
            <h3>전화번호: {{this.$route.params.id.phone}}</h3>
            <h3>병원 URL: {{this.$route.params.id.url}}</h3>
            <h3>영업시간</h3>
            <p>일요일: {{hospitalDatas.sunStart}} ~ {{hospitalDatas.sunFinish}} </p>
            <p>월요일: {{hospitalDatas.monStart}} ~ {{hospitalDatas.monFinish}} </p>
            <p>화요일: {{hospitalDatas.tueStart}} ~ {{hospitalDatas.tueFinish}} </p>
            <p>수요일: {{hospitalDatas.wedStart}} ~ {{hospitalDatas.wedFinish}} </p>
            <p>목요일: {{hospitalDatas.thuStart}} ~ {{hospitalDatas.thuFinish}} </p>
            <p>금요일: {{hospitalDatas.friStart}} ~ {{hospitalDatas.friFinish}} </p>
            <p>토요일: {{hospitalDatas.satStart}} ~ {{hospitalDatas.monFinish}} </p>
            
            


          </b-col>
          <b-col cols="4" style="border: 1px solid grey">
            <hospitalMap style="width: 100%" />
          </b-col>
        </b-row>
      </b-container>

    </div>   

    <br>
    <br>
    <br>
    <br>
    <br>
  </div>
</template>

<script>
import NavBar from '../NavigationBar.vue'
import User from '../../views/accounts/Login.vue'
import http from '@/util/http-common'
import HospitalMap from "../../components/hospital/HospitalMap.vue"

export default {
    name: "HospitalDetail",
    components: {
      NavBar,
      User,
      HospitalMap,
    },
    data() {
      return {
        hospitalDatas: {}
      }
    },
    mounted() {
      http.get(`/hospitals/1`)
      .then(res => {
        this.hospitalDatas = res
      })
    },
}
</script>

<style>
</style>