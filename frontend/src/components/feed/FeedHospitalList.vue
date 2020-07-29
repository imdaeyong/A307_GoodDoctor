<template>
<div>
  <Navbar/>
  <div class="user">
    <div class="wrapC mt-5">
      <h1>병원 리스트 페이지입니다.</h1>
    </div>
  </div>
  <b-container fluid class="bv-example-row bv-example-row-flex-cols" >
    <b-row align-v="stretch">
      <b-col cols="8"  class='border-right' style="background-color: ivory">
        <!-- 아래 div에 for문을 걸어서 돌려서 카드 노출 -->
        <div>
          <b-card no-body class="overflow-hidden my-3" style="max-width: 1000px;">
            <b-row no-gutters>
              <b-col md="2">
                <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
              </b-col>
              <b-col md="10">
                <b-card-body title="가좌동 정형외과" sub-title="닥터 김지효">
                  <b-card-text>
                    요기 의사 완전 야매
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>

          <b-card no-body class="overflow-hidden my-3" style="max-width: 1000px;">
            <b-row no-gutters>
              <b-col md="2">
                <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
              </b-col>
              <b-col md="10">
                <b-card-body title="가좌동 홍내과" sub-title="닥터 방대승">
                  <b-card-text>
                    여기 의사 완전 허준임. 한의학만 못하지 허준임
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>

          <b-card no-body class="overflow-hidden my-3" style="max-width: 1000px;">
            <b-row no-gutters>
              <b-col md="2">
                <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
              </b-col>
              <b-col md="10">
                <b-card-body title="강남 밝은 눈안과"  sub-title="닥터 김대용">
                  <b-card-text>
                    나 여기서 라섹했는데 잘된듯.
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>

          <b-card no-body class="overflow-hidden my-3" style="max-width: 1000px;">
            <b-row no-gutters>
              <b-col md="2">
                <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
              </b-col>
              <b-col md="10">
                <b-card-body title="강남 하늘라섹"  sub-title="닥터 문용호">
                  <b-card-text>
                    요기는 상담만 받아봄 하지는 않았음
                    <br>
                    <a href="">Url: </a>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>


        </div>
      </b-col>

      <b-col cols="4">
        <h1>지도map</h1>

      </b-col>



    </b-row>
  </b-container>
    
</div>
</template>

<script>
import Navbar from "../NavigationBar.vue"

export default {
  name: "FeedHospitalList",
  components: {
    Navbar,
  }
}
</script>

<style>

</style>