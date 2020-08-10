<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <router-link to="/" style="text-decoration: none; color : white;">
        <b-icon-heart-half style="margin-right: 0.2em; margin-top: 0.2em;"></b-icon-heart-half>굿 닥터
      </router-link> 

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>




      <b-collapse id="nav-collapse" is-nav>
          <b-nav-form>
            <b-select v-model="target" class="btn-front" for="navSearch">
              <option value="hospital">병원 검색</option>
              <option value="feed">피드 검색</option>
            </b-select>
            <b-form-input size="sm" type="text" v-model= "word" @keyup.enter="search" class="input-search" placeholder="Search" id="navSearch"></b-form-input>
            <b-button size="sm" @click="search" class="btn-search" style="outline:0;"><b-icon-search></b-icon-search></b-button>
          </b-nav-form>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          
          <b-nav-item href="/feed/write" right>리뷰쓰기</b-nav-item>



          <b-nav-item-dropdown text="병원찾기" right>
            <div class="drop-down-hospital">
              <b-dropdown-item @click="selectSubject('치과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital1.png" alt=""><br>치과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('피부과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital2.png" alt=""><br>피부과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('성형외과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital3.png" alt=""><br>성형외과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('안과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital4.png" alt=""><br>안과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('산부인과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital5.png" alt=""><br>산부인과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('비뇨기과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital6.png" alt=""><br>비뇨기과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('정형외과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital7.png" alt=""><br>정형외과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('마취통증의학과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital8.png" alt=""><br>마취통증의학과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('재활의학과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital10.png" alt=""><br>재활의학과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('영상의학과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital11.png" alt=""><br>영상의학과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('외과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital12.png" alt=""><br>외과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('신경과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital13.png" alt=""><br>신경과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('소아과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital14.png" alt=""><br>소아과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('내과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital15.png" alt=""><br>내과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('이비인후과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital16.png" alt=""><br>이비인후과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('가정의학과')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital17.png" alt=""><br>가정의학과</b-dropdown-item>
              <b-dropdown-item @click="selectSubject('한의원')" class="dropdown-sub-hospital" ><img src="../assets/images/hospital/hospital18.png" alt=""><br>한의원</b-dropdown-item>
            </div>
          </b-nav-item-dropdown>



          <!-- 회원관련 기능 -->
          <b-nav-item-dropdown v-if="this.$store.state.isLogin" :text="this.$store.state.userInfo.data.nickname" right>
            <b-dropdown-item href="/accounts/profile" style="text-decoration : none; color : black;">회원정보</b-dropdown-item>
            <b-dropdown-item v-on:click="logout()">로그아웃</b-dropdown-item>
          </b-nav-item-dropdown>

        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import store from '@/vuex/store.js'
import http from '@/util/http-common'

export default {
  name: "NavigationBar",
  methods:{
    logout(){
      store.dispatch('logout');
      this.$bvModal.show('bv-modal-example');
      this.$router.go(0);
    },
    selectSubject(subject) {
      this.subject = subject;
      this.$router.push(`../hospital?no=10&subject=${subject}`); 
      this.$router.go(0);
    },
    selectGu(gu) {
      if(this.sido=="부산")
        this.gugun="부산"+gu
      else 
        this.gugun=gu

      this.$router.push(`../hospital?no=10&sido=${this.sido}&gu=${this.gugun}`);      
      this.$router.go(0);
    },
    selectCity(city){
      this.sido=city;
      for(var i = 0; i < this.area.length; i++){
        if(city == this.area[i].name) 
          this.area[i].display = !this.area[i].display;
        else 
          this.area[i].display = false;
      }
      city = !city;
    },
    search(){
      if(this.target == 'hospital'){
        this.$router.push(`../search?no=10&word=${this.word}`);
        this.$router.go(0);
      } else if(this.target =='feed'){
        this.$router.push(`../search?word=${this.word}`);
        this.$router.go(0);
      }
    }
  },
  data: () => {
    return {
      sido:"",
      gugun:"",
      subject:"",
      word:"",
      target:"hospital",
      area: [
        {display : false, name : "서울"},
        {display : false, name : "경기"},
        {display : false, name : "부산"},
        {display : false, name : "경남"},
        {display : false, name : "인천"},
        {display : false, name : "대구"},
        {display : false, name : "경북"},    
        {display : false, name : "대전"},
        {display : false, name : "전북"},    
        {display : false, name : "충남"},
      ]
    }
  }
}
</script>