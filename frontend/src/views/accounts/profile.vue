<template>
  <div>
    <NavBar/>
    <div class="user">
      <div class="wrapC mt-5">
        <h1 style="text-align:center;">회원 정보</h1>
      </div>
    </div>
    <b-container fluid class="bv-example-row bv-example-row-flex-cols">
      <b-row align-v="stretch">

        <b-col class='border-right' cols="3" style="text-align:center">
          <h1 class="mt-3">index</h1>
          <button @click="toggle('myProfile')" class="mt-3" style="outline: 0"><h5>내 정보</h5></button>
          <br>  
          <button @click="toggle('myHospital')" class="mt-3" style="outline: 0"><h5>관심 정보</h5></button>
          <br>  
          <button @click="toggle('myReview')" class="mt-3" style="outline: 0"><h5>내 리뷰</h5></button>
        </b-col>
        
        <b-col cols="9" style="text-align:center;">
          <div v-if="categories[0].display">
            <myProfile/>
          </div>

          <div v-if="categories[1].display">
            <myHospital/>
          </div>

          <div v-if="categories[2].display">
            <myReview/>
          </div>

        </b-col>
        
      </b-row>
    </b-container>
  </div>
</template>

<script>
import '../../assets/css/style.scss'
import '../../assets/css/user.scss'
import NavBar from "../../components/NavigationBar.vue";
import myProfile from "../../components/accounts/profile/myProfile.vue"
import myHospital from "../../components/accounts/profile/myHospital.vue"
import myReview from "../../components/accounts/profile/myReview.vue"

export default {
  name: 'Profile',
  components: {
    NavBar,
    myProfile,
    myHospital,
    myReview
  },
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

<style>

</style>