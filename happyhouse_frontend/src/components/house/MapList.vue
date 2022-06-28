<template>
  <div cols="12" md="12">
    <div id="map">
      <v-btn id="btnRoadView" @click="showRoadView">로드뷰</v-btn>
      <div id="roadView"></div>
    </div>
    <v-row>
      <v-checkbox
        v-model="chkTraffic"
        :label="`교통정보 보기`"
        @click="setOverlayMapTypeId"
        class="mr-2"
        >교통정보 보기</v-checkbox
      >
      <v-checkbox
        v-model="chkBicycle"
        :label="`자전거도로 정보 보기`"
        class="mr-2"
        @click="setOverlayMapTypeId"
      ></v-checkbox>
      <v-checkbox
        v-model="chkTerrain"
        :label="`지형 정보 보기`"
        @click="setOverlayMapTypeId"
        class="mr-2"
      ></v-checkbox>
    </v-row>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "MapList",

  data: () => ({
    markers: [],
    infowindow: null,
    terrain: null,
    traffic: null,
    bicycle: null,
    useDistrict: null,
    chkTraffic: false,
    chkBicycle: false,
    chkTerrain: false,
    marker: null,
    curlat: 0,
    curlng: 0,
    roadView: false,
    rvContainer: null,
    rv: null,
    rvClient: null,
    rvMarker: null,
    map: null,
  }),
  props: {
    house: null,
  },
  created() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.curlat = position.coords.latitude;
      this.curlng = position.coords.longitude;
    });
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
  },
  watch: {
    houses: function () {
      // console.log(this.houses);
      this.displayMarkers(this.houses);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
      // this.displayMarker(houses);
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }
    setTimeout(() => {
      navigator.geolocation.getCurrentPosition((position) => {
        this.curlat = position.coords.latitude;
        this.curlng = position.coords.longitude;
        let locPosition = new kakao.maps.LatLng(this.curlat, this.curlng); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
        // message = '<div style="padding:5px;">현재 위치</div>'; // 인포윈도우에 표시될 내용입니다

        // 마커와 인포윈도우를 표시합니다
        this.displayMarker(locPosition);
        this.displayMarkers(this.houses);
      });
      this.terrain = kakao.maps.MapTypeId.TERRAIN;
      this.traffic = kakao.maps.MapTypeId.TRAFFIC;
      this.bicycle = kakao.maps.MapTypeId.BICYCLE;
      this.useDistrict = kakao.maps.MapTypeId.USE_DISTRICT;
    }, 500);
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.curlat, this.curlng),
        level: 5,
      };

      this.map = new kakao.maps.Map(container, options);

      var mapTypeControl = new kakao.maps.MapTypeControl();

      // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      var zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },
    changeSize(wid, hei) {
      const container = document.getElementById("map");
      container.style.width = `${wid}px`;
      container.style.height = `${hei}px`;
      this.map.relayout();
    },
    displayMarker(locPosition) {
      const imageSrc = require("../../assets/star.png");
      const imageSize = new kakao.maps.Size(40, 40);
      const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
      this.marker = new kakao.maps.Marker({
        map: this.map,
        position: locPosition,
        image: markerImage,
      });
      var infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding: 10px;text-align: center;width:150px;"><h5>현재 위치</h5></div>`, // 인포윈도우에 표시할 내용
      });

      infowindow.open(this.map, this.marker);

      // 지도 중심좌표를 접속위치로 변경합니다
      this.map.setCenter(locPosition);
    },
    displayMarkers(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      this.markers = [];
      const positions = markerPositions.map(
        (house) => new kakao.maps.LatLng(house.lat, house.lng),
      );

      const informations = markerPositions.map((house) => ({
        position: new kakao.maps.LatLng(house.lat, house.lng),
        content: `<div style="padding: 10px;text-align:center;width: 150px"><h5>${house.aptName}</h5></div>`,
        house: house,
      }));

      for (var i = 0; i < informations.length; i++) {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: informations[i].position, // 마커의 위치
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: informations[i].content, // 인포윈도우에 표시할 내용
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(
          marker,
          "mouseover",
          this.makeOverListener(this.map, marker, infowindow),
        );
        kakao.maps.event.addListener(
          marker,
          "mouseout",
          this.makeOutListener(infowindow),
        );
        kakao.maps.event.addListener(
          marker,
          "click",
          this.moveToDetail(informations[i].house),
        );
        this.markers.push(marker);
      }
      if (positions.length > 0) {
        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds(),
        );

        this.map.setBounds(bounds);
      }
    },
    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },
    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },
    moveToDetail(house) {
      var self = this;
      return function () {
        self.detailHouse(house);
        if (self.$route.path !== "/map/detail")
          self.$router.push("/map/detail");
      };
    },
    setOverlayMapTypeId() {
      this.map.removeOverlayMapTypeId(this.traffic);
      this.map.removeOverlayMapTypeId(this.bicycle);
      this.map.removeOverlayMapTypeId(this.terrain);
      if (this.chkTraffic) this.map.addOverlayMapTypeId(this.traffic);
      if (this.chkBicycle) this.map.addOverlayMapTypeId(this.bicycle);
      if (this.chkTerrain) this.map.addOverlayMapTypeId(this.terrain);
    },
    showRoadView() {
      if (!this.roadView) {
        this.roadView = !this.roadView;
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        this.rvContainer = document.getElementById("roadView"); //로드뷰를 표시할 div
        this.rv = new kakao.maps.Roadview(this.rvContainer); //로드뷰 객체
        this.rvClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
        this.rvContainer.style.display = "none";
        this.rvContainer.style.height = "0";
        this.rvContainer.style.right = "0";
        this.map.relayout();

        // 마커 이미지를 생성합니다.
        var markImage = new kakao.maps.MarkerImage(
          "https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png",
          new kakao.maps.Size(26, 46),
          {
            // 스프라이트 이미지를 사용합니다.
            // 스프라이트 이미지 전체의 크기를 지정하고
            spriteSize: new kakao.maps.Size(1666, 168),
            // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
            // background-position으로 지정하는 값이며 부호는 반대입니다.
            spriteOrigin: new kakao.maps.Point(705, 114),
            offset: new kakao.maps.Point(13, 46),
          },
        );

        // 드래그가 가능한 마커를 생성합니다.
        this.rvMarker = new kakao.maps.Marker({
          image: markImage,
          position: this.map.getCenter(),
          draggable: true,
          map: this.map,
        });
        var self = this;
        //마커에 dragend 이벤트를 할당합니다
        kakao.maps.event.addListener(this.rvMarker, "dragend", function () {
          var position = self.rvMarker.getPosition(); //현재 마커가 놓인 자리의 좌표
          self.toggleRoadview(position); //로드뷰를 토글합니다
        });

        //지도에 클릭 이벤트를 할당합니다
        kakao.maps.event.addListener(this.map, "click", function (mouseEvent) {
          // 현재 클릭한 부분의 좌표를 리턴
          var position = mouseEvent.latLng;

          self.rvMarker.setPosition(position);
          self.toggleRoadview(position); //로드뷰를 토글합니다
        });
      } else {
        this.roadView = !this.roadView;
        this.rvMarker.setMap(null);
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        this.rvContainer.style.display = "none";
      }
    },
    toggleRoadview(position) {
      var self = this;
      //전달받은 좌표(position)에 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄웁니다
      this.rvClient.getNearestPanoId(position, 50, function (panoId) {
        if (panoId === null) {
          self.rvContainer.style.display = "none"; //로드뷰를 넣은 컨테이너를 숨깁니다
          self.rvContainer.style.height = "0%";
          self.map.relayout();
        } else {
          self.map.relayout(); //지도를 감싸고 있는 영역이 변경됨에 따라, 지도를 재배열합니다
          self.rvContainer.style.display = "block"; //로드뷰를 넣은 컨테이너를 보이게합니다
          self.rvContainer.style.height = "50%";
          self.rvContainer.style.right = "0";
          self.rv.setPanoId(panoId, position); //panoId를 통한 로드뷰 실행
          self.rv.relayout(); //로드뷰를 감싸고 있는 영역이 변경됨에 따라, 로드뷰를 재배열합니다
        }
      });
    },
  },
};
</script>

<style scoped>
#map {
  width: 1000px;
  height: 800px;
}
#btnRoadView {
  position: absolute;
  top: 5px;
  left: 5px;
  padding: 7px 12px;
  font-size: 14px;
  border: 1px solid #dbdbdb;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.04);
  z-index: 3;
  cursor: pointer;
}
#roadView {
  position: absolute;
  width: 50%;
  right: 0;
  top: 0;
  z-index: 2;
}
</style>
