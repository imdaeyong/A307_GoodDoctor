<template>
  <div>
    <h2>'{{hospitals.data[0].subject}}'에 대한 검색 결과입니다.</h2>
    <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
      <b-card no-body @click="hospitalDataSend(hospital)"
        @mouseover="doMouseOver(hospital)" class="overflow-hidden my-3 btn-left"
        style="height: 10rem; padding : 1em;">

        <div>
          <b-row style="float: left">            
            <b-col md="3">
              <img src="../../assets/images/hospital/default-doctor.png" alt="Image" class="hospital-doctor">
            </b-col>
            <b-col md="8" style="padding-left: 3em">
              <div class="hospital-info">
                <div class="mt-1"><span>{{hospital.name}}</span></div>
                <div class="mt-2">평점| 리뷰수</div>
                <div class="mt-2">진료과목 : {{hospital.subject}}</div>
              </div>
            </b-col>            
          </b-row>
          <button style="padding-left: 5em" @click.stop="addFavorites(hospital)" z-index=5 width=40px;><img src="../../assets/images/hospital/favorite.png" alt="favorites_Button"></button>
        </div>
      </b-card>
    </div>
  </div>
</template>


<script>
import http from "@/util/http-common";
import '../../assets/css/hospital.scss'

export default {
  name: "HospitalItem",
  data: () => {
    return {
      hospitals: [],
      pageLimit: 10,
      seletDatsId: "",
    };
  },
  created() {
    this.initComponent();
  },
  watch: {
    "$route.query": function () {
      this.initComponent();
    },
  },
  methods: {
    addFavorites(hospital,event){
      // event.stopPropagation()
      var userId = this.$store.getters.userInfo.data.id
      var favorites= []
      if(localStorage.getItem(userId)){
        favorites = JSON.parse(localStorage[userId]);

        var isExist = false;
        for(var i=0; i<favorites.length;i++){
          if(favorites[i].id==hospital.id){
            favorites.splice(favorites.indexOf(favorites[i]),1);
            alert(hospital.name+"이 즐겨찾기에서 제거되었습니다.")
            isExist=true;
            break;
          }
        }
        if(!isExist){
          alert(hospital.name+"이 즐겨찾기에 추가되었습니다")
          favorites.push(hospital)
        }
      }else{
        favorites.push(hospital)
        console.log("사용자의 즐겨찾기리스트 생성")
      }
      localStorage.setItem(userId,JSON.stringify(favorites));
    },
    doMouseOver(hospital) {
      this.$store.commit('addHospitalHover',hospital)
    },
    hospitalDataSend(hospital) {
      this.$store.commit('mutateHospital',hospital)
      this.$router.push({name: "HospitalDetail"})
    },
    hospitalZoom(hospital){
      this.$store.commit('addHospitalZoom',hospital)
    },

    initComponent() {
      http
        .get("hospitals/", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            subject: this.$route.query.subject,
            sido: this.$route.query.sido,
            gu: this.$route.query.gu,
            word: "",
            lon:37.5012743,
            lat:127.039585,
  
          },
        })
        .then((data) => {
          this.hospitals = data;
        })
        .catch((error) => {
          alert("에러가 발생했습니다.");
        });
    },
  },
};
</script>