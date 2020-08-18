<template>
  <div class="my-3" id="app" style="position: fixed;">
    <div id="map"></div>
    <input type="hidden" id="lat" v-model="lat">
    <input type="hidden" id="lon" v-model="lon">
  </div>
</template>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJ3iLRNnx8MpswGdoR69UQOtGSQltPDZQ"></script>

<script>
import http from '@/util/http-common'

export default {
  name: 'HospitalMap',
  data: () => {
    return {
      map: null,
      hospitalLoc: [],
      pageLimit: 10,
      markers:[],
      infowindows:[],
      my_lon:37.5012743,
      my_lat:127.039585,
    };
  },

  watch: {
    "$route.query": function () {
      this.initComponent();
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initComponent();
      } else {
        const script = document.createElement("script");
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initComponent());
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
      }else if(mutation.type=="addHospitalHover"){
        var hover_hos = this.$store.getters.hospitalHover;
        var overlay = new kakao.maps.LatLng(hover_hos.lng,hover_hos.lat)
        this.map.setLevel(3); //마커 눌렀을때
        this.map.setCenter(overlay)

        for(var i=0; i<this.markers.length; i++){
          var marker = this.markers[i]
            if(hover_hos.name==marker.getTitle()) {
              this.infowindows[i].open(this.map, marker);
            }else{
              this.infowindows[i].close();
            }
        }
      }
    })
  },
  methods: {
    initComponent() {
      navigator.geolocation.getCurrentPosition(function(position) {
      var c_lat = position.coords.latitude, // 위도
          c_lon = position.coords.longitude; // 경도    
          lat.value=c_lat;
          lon.value=c_lon;
          this.my_lon=c_lon;
          this.my_lat=c_lat;
          console.log(c_lat)
        });
        console.log(this.my_lat)
      http
        .get("hospitals/", {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
            subject: this.$route.query.subject,
            sido: this.$route.query.sido,
            gu: this.$route.query.gu,
            word : "",
            lon:this.my_lon,
            lat:this.my_lat,
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
        require("../../assets/images/hospital/custom-marker.png");
      var imageSize = new kakao.maps.Size(25, 37);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      var my_loc = new kakao.maps.LatLng(lat.value,lon.value);
      var map = new kakao.maps.Map(document.getElementById("map"), {
        center: my_loc, // 지도의 중심좌표
        level: 13, // 지도의 확대 레벨
      });

      var my_loc = new kakao.maps.LatLng(lat.value,lon.value);
      console.log("initmap안에서")
      console.log(my_loc)

      var my_loc_marker = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 

      var my_loc_markerImage = new kakao.maps.MarkerImage(my_loc_marker, imageSize);

      //현재위치 마커   
      var my_marker = new kakao.maps.Marker({  
          map: map, 
          position: my_loc,
          image:my_loc_markerImage
      }); 

      // 커스텀 오버레이를 생성합니다
      var customOverlay = new kakao.maps.CustomOverlay({
          position: my_loc,
          content: '<div class ="label"><span class="center" style="letter-spacing: 1px; font-family: NanumGothic,\'Malgun Gothic\',dotum,\'돋움\',sans-serif; color: #333; ">현재위치</div>'   
      });

      // 커스텀 오버레이를 지도에 표시합니다
      customOverlay.setMap(map);
    

      var markers =[];
      var infowindows = [];
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
        
        var iwContent = '<div style="text-align:center; margin-right : -145px;">'+
                        '<span style="color: #17a2b8; font-size:1.1em; font-weight:;">'
                        +info.name+'</span><br>'
                        +'<span style="font-size:0.9em; margin-top : -3em">'+info.gu+'</span>'+
                        '<br></div>',
        iwRemoveable = false;

        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent
        });
        infowindows.push(infowindow)
        
        kakao.maps.event.addListener(marker, 'mouseover', this.iwOpen(map,marker,infowindow,infowindows));
        kakao.maps.event.addListener(marker, 'mouseout', this.iwClose(infowindow));
        kakao.maps.event.addListener(marker, 'click', this.markerClick(marker,map));
        markers.push(marker);
      }
      map.setCenter(new kakao.maps.LatLng(centerLng/this.hospitalLoc.length, centerLat/this.hospitalLoc.length));

      // 마커 클러스터러를 생성합니다 
      var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
          averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
          minLevel: 8, // 클러스터 할 최소 지도 레벨 
          calculator: [3, 5, 10], // 클러스터의 크기 구분 값, 각 사이값마다 설정된 text나 style이 적용된다
          disableClickZoom: true,
      });

      kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
        var level = map.getLevel(); 
        if(level<=2){
          map.setLevel(1);
           map.setCenter(cluster.getCenter());
        }else{            
          // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다f
          map.setLevel(level-2, {anchor: cluster.getCenter()});
          map.setCenter(cluster.getCenter());
        }       
      });
      clusterer.addMarkers(markers);
      
      if(clusterer._clusters.length==1){
        map.setLevel(7); //처음 지도 거리
      }
      this.infowindows=infowindows;
      this.markers=markers;
      this.map=map;
    },
    iwOpen(map,marker,infowindow,infowindows){
      return function() {
        this.infowindows = infowindows;
        for(var i=0; i<this.infowindows.length; i++){
          this.infowindows[i].close();
        }
        infowindow.open(map, marker);
      }
    },
    iwClose(infowindow){
       return function() {
          infowindow.close();
       }
    },
    markerClick(marker,map){
      return function() {
         map.setCenter(marker.getPosition())
         map.setLevel(3);
      }
    }
  }
};
</script>

<style scoped>
#map {
  width: 500px;
  height:600px;
}
</style>
