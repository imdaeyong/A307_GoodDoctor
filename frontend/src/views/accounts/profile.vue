<template>
  <div>
    <NavBar/>

    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row align-v="stretch">
        <b-col class='mt-5' cols="2" style="text-align:center">
          <h3>굿닥터 회원 정보</h3>
          <div v-if="categories[0].display" style="background-color: #17a2b8; border-radius: 10px;">
            <button @click="toggle('myProfile')" class="Profile abled" style="outline: 0">
              <p>내 정보</p>
            </button>
          </div>
          <div v-else>
            <button @click="toggle('myProfile')" class="Profile disabled" style="outline: 0">
              <p>내 정보</p>
            </button>
          </div>
          
          <div v-if="categories[1].display" style="background-color: #17a2b8; border-radius: 10px;">
            <button @click="toggle('myHospital')" class="Profile abled" style="outline: 0">
              <p>즐겨찾는 병원</p>
            </button>
          </div>
          <div v-else>
            <button @click="toggle('myHospital')" class="Profile disabled" style="outline: 0">
              <p>즐겨찾는 병원</p>
            </button>
          </div>

          <div v-if="categories[2].display" style="background-color: #17a2b8; border-radius: 10px;">
            <button @click="toggle('myReview')" class="Profile abled" style="outline: 0">
              <p>내 리뷰</p>
            </button>
          </div>
          <div v-else>
            <button @click="toggle('myReview')" class="Profile disabled" style="outline: 0">
              <p>내 리뷰</p>
            </button>
          </div>
        </b-col>
        
        <b-col class='mt-5' cols="10" style="text-align:center;">
          <div v-if="categories[0].display">
            <myProfile/>
          </div>
          <div v-if="categories[1].display">
            <myHospital/>
          </div>
          <div v-if="categories[2].display">
            <myReview style="text-align:left;"/>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import NavBar from "../../components/NavigationBar.vue"
import myProfile from "../../components/accounts/profile/myProfile.vue"
import myHospital from "../../components/accounts/profile/myHospital.vue"
import myReview from "../../components/accounts/profile/myReview.vue"
import store from '@/vuex/store.js'
import FeedModal from "../../../src/components/feed/FeedModal.vue";

export default {
  name: 'Profile',
  components: {
    NavBar,
    myProfile,
    myHospital,
    myReview,
  },
  mounted(){
    if(!store.state.isLogin) this.$bvModal.show('bv-modal-example');
  },
  methods: {
    toggle(text) {
      for(var i = 0; i < this.categories.length; i++) {
        if(text == this.categories[i].name) {
          this.categories[i].display = true
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