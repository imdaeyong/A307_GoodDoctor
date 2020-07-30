<template>

    <div>
      <div v-for="hospital in hospitals.data" v-bind:key="hospital.id">
        <b-card no-body class="overflow-hidden my-3 ml-4" style="max-width: 1000px;">
          <b-row no-gutters>
            <b-col md="2">
              <b-card-img src="https://picsum.photos/400/400/?image=20" alt="Image" class="rounded-0"></b-card-img>
            </b-col>
            <b-col md="10">
              <b-card-body>
                <b-card-title>{{hospital.name}}</b-card-title>
                <b-card-text>주소 : {{hospital.address}}</b-card-text>
                <b-card-text>전화번호 : {{hospital.phone}}</b-card-text>
                <b-card-text>진료과목 : {{hospital.subject}}</b-card-text>
              </b-card-body>
            </b-col>
          </b-row>
        </b-card>
      </div>
  <div id="map"></div>
  </div>
</template>

<script>
  import axios from "axios";

    export default {
        name : 'list',
        data: () => {
            return {
                map: null,
                hospitals: [], 
                pageLimit: 10, 
                pageOffet: 0
                };
        },
        created() {
            this.initComponent();

            if (window.kakao && window.kakao.maps) {
            this.initMap();
          } else {
            const script = document.createElement("script");
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src =
              "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8cbe81440a2dc401533a67159970a3ac";
            document.head.appendChild(script);
          }
        },
        watch: {
            '$route.query': function () {
                this.initComponent();
            }
        },
        methods: {
            initComponent() {
                axios
                    .get('http://localhost:8080/hospitals/pagelink', {
                        params: {
                            limit: this.pageLimit,
                            offset: `${this.$route.query.no - this.pageLimit}`,
                            subject : this.$route.query.subject,
                            sido : this.$route.query.sido,
                            gu : this.$route.query.gu,
                        }
                    })
                    .then((data) => {
                        this.hospitals = data;
                        this.initMap();
                    })
                    .catch((error) => {
                        console.log(error.response);
                        alert('에러가 발생했습니다.');
                    });
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
                level: 10, // 지도의 확대 레벨
              });
              var marker = new kakao.maps.Marker({
                position: multi,
              });

              for (var i = 0; i < this.hospitals.data.length; i++) {
                console.log(this.hospitals.data[i].lat, this.hospitals.data[i].lng);

                marker = new kakao.maps.Marker({
                  position: new kakao.maps.LatLng(
                    this.hospitals.data[i].lng,
                    this.hospitals.data[i].lat
                  ),
                  title: "병원", //marker.mc에 title담기는거같음
                  image: markerImage,
                  clickable: true, //마커 클릭시 지도 동작x
                  map: map,
                });
              }
              map.setCenter(new kakao.maps.LatLng(this.hospitals.data[0].lng,this.hospitals.data[0].lat))
          },
        },
    }
</script>

<style>
#map {
  width: 500px;
  height: 400px;
}
</style>