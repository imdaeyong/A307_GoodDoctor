<template>
  <div>
    <NavBar/>
    <div class="user">
      <div class="wrapC mt-5">
        <h1>프로필화면 입니다.</h1>
      </div>
    </div>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row align-v="stretch">

        <b-col cols="3" style="background-color:red; text-align:center;">
          <h1 class="mt-3">index</h1>
          <button @click="toggle('myProfile')">내 정보</button>
          <button @click="toggle('myHospital')">관심 정보</button>
          <button @click="toggle('myReview')">내 리뷰</button>
        </b-col>
        
        <b-col cols="9" style="background-color:ivory; text-align:center;">
          <div v-if="categories[0].display">
            <h1 class="mt-3">내 정보</h1>
            <h1>닉네임, 아이디, 비밀번호 연결할 페이지</h1>
            <b-row>
              <div>
                <img src= "../../assets/images/profile_default.png" alt="" style="width: 14rem">
                <div class="user-info-modal">닉네임</div>
              </div>
              <b-col >
                <p>닉네임:</p>
                <p>아이디(이메일):</p>
                <button class="btn-full-center mt-4"><a href="/accounts/changepassword">비밀번호 변경</a></button>
              </b-col>
            </b-row>
          </div>

          <div v-if="categories[1].display">
            <h1 class="mt-3">내 관심 정보</h1>
            <h1>내가 관심있어하는 정보를 확인 및 수정합니다. </h1>
          </div>

          <div v-if="categories[2].display">
            <h1 class="mt-3">내 리뷰</h1>
            <h1>내가 작성한 리뷰를 확인합니다.</h1>
            <FeedModal/>
          </div>

        </b-col>
        
      </b-row>
    </b-container>
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import FeedModal from "../../components/feed/FeedModal.vue";
import NavBar from "../../components/NavigationBar.vue";
export default {
  name: 'Profile',
   components: { FeedModal, NavBar},
  methods: {
    toggle(text) {
      for(var i = 0; i < this.categories.length; i++) {
        if(text == this.categories[i].name) {
          this.categories[i].display = !this.categories[i].display
        } else {
          this.categories[i].display = false
        }
      }
    }
  },
  data() {
    return {
      categories: [
      {display: true, name: 'myProfile'},
      {display: false, name: 'myHospital'},
      {display: false, name: 'myReview'}
      ]
    }
  }
}
</script>