<template>
  <div id="app">
    <div id="map"></div>
  </div>
</template>

<script>
var map;
let loc_lat;
let loc_lon;

export default {
  data: () => {
    return {
        markerPositions1: [
            [33.452278, 126.567803],
            [33.452671, 126.574792],
            [33.451744, 126.572441]
        ],
        curLat: "",
        curLon: "",
        markers:[],
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
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8cbe81440a2dc401533a67159970a3ac";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var multi = new kakao.maps.LatLng(37.5012743, 127.039585);
      var map = new kakao.maps.Map(document.getElementById('map'), {
      center : multi, // 지도의 중심좌표
      level : 10   // 지도의 확대 레벨
      });
      let marker = new kakao.maps.Marker({
          position: multi
      });
      marker.setMap(map);

    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    }
  }
};
</script>

<style>
#map {
  width: 400px;
  height: 300px;
}
</style>