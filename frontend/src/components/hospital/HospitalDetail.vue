<template>
  <div>
    <NavBar/>
    <b-modal id="bv-modal-example" hide-footer hide-header no-close-on-backdrop no-close-on-esc>   
      <User/>
    </b-modal>


    <div class="HospitalDetail mx-auto">
      <h1 class="d-flex justify-content-center mt-5 mb-4">
        <span style="color : #17a2b8">{{this.$route.params.id}} / 병원</span> 정보에요.
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

        <div class="row ml-4" style="width: 255px">
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

        <h4>..........<hr>여기에는 가로 <hr>무한 스크롤을<hr> 구현해서<hr> 옆으로 넘기면서<hr>
          리뷰를 <hr>쭉쭉 보여줘요
        </h4>
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
            <h3>위치: {{this.hospitalDatas.data.address}}</h3>
            <h3>전화번호: {{this.hospitalDatas.data.phone}}</h3>
            <h3>병원 URL: {{this.hospitalDatas.data.url}}</h3>
            <h3>영업시간: </h3>
            그밖에 추가 정보가 있으면 더 좋겠죠

          </b-col>
          <b-col cols="4" style="border: 1px solid grey">
            여<hr>어<hr>어<hr>어<hr>어<hr>긴<hr>
            지도가<hr>
            나올<hr>s
            거에요


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

export default {
    name: "HospitalDetail",
    components: {
      NavBar,
      User
    },
    data() {
      return {
        hospitalDatas: {}
      }
    },
    created() {
      http.get(`/hospitals/${this.$route.params.id}`)
      .then(res => {
        this.hospitalDatas = res
      })
    },
}
</script>

<style>
</style>