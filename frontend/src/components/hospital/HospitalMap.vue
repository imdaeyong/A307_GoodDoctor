<template>
  <div class="my-3" id="app">
    <div id="map"></div>
  </div>
</template>

<script>
import axios from "axios";
import http from '@/util/http-common'
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
    this.$store.subscribe((mutation, state) => {
      if(mutation.type=="addHospitalZoom"){
        var zoomer = this.$store.getters.hospitalZoom;
        this.map.setLevel(3);
        this.map.setCenter(new kakao.maps.LatLng(zoomer.lng,zoomer.lat))
      }
    })
    
  },
  methods: {
    initComponent() {
      http
        .get("/hospitals/pagelink", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            subject: this.$route.query.subject,
            sido: this.$route.query.sido,
            gu: this.$route.query.gu,
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
        level: 14, // 지도의 확대 레벨
      });

      var markers =[];
      var centerLng=0;
      var centerLat=0;
      for (var i = 0; i < this.hospitalLoc.length; i++) {
        var info = this.hospitalLoc[i];
        centerLng+=info.lng;
        centerLat+=info.lat;
        
        var marker = new kakao.maps.Marker({
          position : new kakao.maps.LatLng(info.lng, info.lat),
          title:info.name, 
          image:markerImage,
          clickable:true //마커 클릭시 지도 동작x
        });

        var iwContent = '<div style="display:inline-block; width:auto;text-align:center;">'+info.name+'</div>',
        iwRemoveable = false;

        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent
        });

        kakao.maps.event.addListener(marker, 'click', this.hospitalDetail(info.id));
        kakao.maps.event.addListener(marker, 'mouseover', this.iwOpen(map,marker,infowindow));
        kakao.maps.event.addListener(marker, 'mouseout', this.iwClose(infowindow));
        
        markers.push(marker);
      }
      map.setCenter(new kakao.maps.LatLng(centerLng/10, centerLat/10));

      // 마커 클러스터러를 생성합니다 
      var clusterer = new kakao.maps.MarkerClusterer({
          map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
          averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
          minLevel: 2, // 클러스터 할 최소 지도 레벨 
          calculator: [3, 5, 10], // 클러스터의 크기 구분 값, 각 사이값마다 설정된 text나 style이 적용된다
          disableClickZoom: true,
      });

      kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
       var level = map.getLevel(); 
        if(level<=2){
           map.setLevel(1);
           map.setCenter(cluster.getCenter());
        }else{            
          // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
          map.setLevel(level-2, {anchor: cluster.getCenter()});
          map.setCenter(cluster.getCenter());
        }       
    });
      clusterer.addMarkers(markers);
      
      if(clusterer._clusters.length==1){
        map.setLevel(10);
      }
      this.map=map;
    },
    hospitalDetail(id){
      return function() {
        http.get('/hospitals/'+id)
          .then(data => {
            //데이터 받아오기
          })
          .catch(err => {
            //데이터 받아오기 오류
          })   
      }
    },
    iwOpen(map,marker,infowindow){
       return function() {
         infowindow.open(map, marker);
       }
    },
    iwClose(infowindow){
       return function() {
         infowindow.close();
       }
    }
  },
};
</script>

<style>
#map {
  width: 500px;
  height:600px;
}
</style>