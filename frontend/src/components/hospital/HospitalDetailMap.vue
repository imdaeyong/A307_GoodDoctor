<template>
  <div class="my-3" id="app">
    <div id="map"></div>
  </div>
</template>
<script>
import http from '@/util/http-common'

export default {
  props:{
    hospitalInfo:[Object],
  },

  name: 'HospitalDetailMap',
  data: () => {
    return {
      map: null,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8cbe81440a2dc401533" +
        "a67159970a3ac";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      //마커 이미지!
      var imageSrc =
        require("../../assets/images/hospital/custom-marker.png");
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      var hospital = new kakao.maps.LatLng(this.hospitalInfo.lng, this.hospitalInfo.lat);
      var map = new kakao.maps.Map(document.getElementById("map"), {
        center: hospital, // 지도의 중심좌표
        level: 2, // 지도의 확대 레벨
      });
      var iwContent = '<div style="text-align:center; background-color: white;margin-bottom : 125px; border:1px solid #17a2b8">'+
                        '<span style="color: #17a2b8; font-size:1.1em;">'
                        +this.hospitalInfo.name+'</span><br>'
                        +'<span style="font-size:0.9em; margin-top : -3em">'+this.hospitalInfo.gu+'</span>'+
                        '<br></div>',
        iwRemoveable = false;

     // 커스텀 오버레이를 생성합니다
      var customOverlay = new kakao.maps.CustomOverlay({
          position: hospital,
          content: iwContent,
          map:map,
      });

       var marker = new kakao.maps.Marker({
          position : hospital,
          title:this.hospitalInfo.name, 
          image:markerImage,
          clickable:true, //마커 클릭시 지도 동작x
          map:map,
        });
    },
  }
};
</script>

<style scoped>
#map {
  width: 300px;
  height:500px;
}
</style>
