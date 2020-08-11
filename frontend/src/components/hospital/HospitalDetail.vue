<template>
  <div>
    <NavBar/>
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>

    <div class="HospitalDetail mx-auto">
      <h1 class="d-flex justify-content-center mt-5 mb-4">
        <span style="color: #17a2b8">{{this.$route.params.id.name}}</span> 정보에요.
      </h1>
      
      <!-- 가로 무한 스크롤 -피드노출 -->
      <h3 >선택한 병원의 리뷰 목록</h3>
      <div class="row mt-3">
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


      <!-- API 정보 -->
      <div class="ml-3" style="text-align: left; margin-top: 100px;">
        <h4>위치 & 정보</h4>
        <p>※주의사항 : 방문 전, 전화로 확인 후 이용해주세요.</p>
      </div>
      <b-container class="bv-example-row" style="float: left;">
        <b-row>
          <b-col cols="8" style="border: 1px solid grey; text-align: left;">
            <h5 class="mt-3">🏣 위치: {{this.$route.params.id.address}}</h5>
            <h5>📞 전화번호: {{this.$route.params.id.phone}}</h5>
            <h5>💻 병원 URL: {{this.$route.params.id.url}}</h5>
            <h5>🌞 주간응급실: {{hospitalDatas.data.emergencyDay}} / 🌛 야간응급실: {{hospitalDatas.data.emergencyNight}}</h5>
            <hr>
            <h5>🥼 의사 정보</h5>
            <p>전공의: {{hospitalDatas.data.doctor}}명</p>
            <p>인턴: {{hospitalDatas.data.internist}}명</p>            
            <hr>
            <h5>📝 영업시간</h5>
            <p>일요일: {{hospitalDatas.data.sunStart}} ~ {{hospitalDatas.data.sunFinish}} </p>
            <p>월요일: {{hospitalDatas.data.monStart}} ~ {{hospitalDatas.data.monFinish}} </p>
            <p>화요일: {{hospitalDatas.data.tueStart}} ~ {{hospitalDatas.data.tueFinish}} </p>
            <p>수요일: {{hospitalDatas.data.wedStart}} ~ {{hospitalDatas.data.wedFinish}} </p>
            <p>목요일: {{hospitalDatas.data.thuStart}} ~ {{hospitalDatas.data.thuFinish}} </p>
            <p>금요일: {{hospitalDatas.data.friStart}} ~ {{hospitalDatas.data.friFinish}} </p>
            <p>토요일: {{hospitalDatas.data.satStart}} ~ {{hospitalDatas.data.monFinish}} </p>
            
            

          </b-col>
          <b-col cols="4" style="border: 1px solid grey">
            <hospitalDetailMap :hospitalInfo="this.hospitalDatas.data"></hospitalDetailMap>
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
import HospitalDetailMap from "../../components/hospital/HospitalDetailMap.vue"

export default {
    name: "HospitalDetail",
    components: {
      NavBar,
      User,
      HospitalDetailMap,
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

<style scoped>
h5 {
  margin-bottom: .6em;
}
p {
  margin-bottom: 6px;
}
</style>