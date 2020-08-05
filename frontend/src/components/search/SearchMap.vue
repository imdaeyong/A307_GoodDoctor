<template>
  <div class="my-3" id="app">
    <div id="map"></div>
  </div>
</template>

<script>
import axios from "axios";
import http from "@/util/http-common";
export default {
  data: () => {
    return {
      map: null,
      hospitalLoc: [],
      pageLimit: 10,
      pageOffet: 0,
    };
  },

  watch: {
    "$route.query": function () {
      this.initComponent();
      this.initMap();
    },
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
    initComponent() {
      http
        .get("/search/pagelink", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            word: this.$route.query.word,
          },
        })
        .then((response) => {
          this.hospitalLoc = response.data;
          this.initMap();
        })
        .catch((error) => {});
    },
    initMap() {
      //마커 이미지!
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      var multi = new kakao.maps.LatLng(37.5012743, 127.039585);
      var map = new kakao.maps.Map(document.getElementById("map"), {
        center: multi, // 지도의 중심좌표
        level: 9, // 지도의 확대 레벨
      });
      var marker = new kakao.maps.Marker({ position: multi });

      for (var i = 0; i < this.hospitalLoc.length; i++) {
        marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(
            this.hospitalLoc[i].lng,
            this.hospitalLoc[i].lat
          ),
          title: "병원", //marker.mc에 title담기는거같음
          image: markerImage,
          clickable: true, //마커 클릭시 지도 동작x
          map: map,
        });
      }
      map.setCenter(
        new kakao.maps.LatLng(this.hospitalLoc[1].lng, this.hospitalLoc[1].lat)
      );
    },
  },
};
</script>

<style>
#map {
  width: 400px;
  height: 300px;
}
</style>