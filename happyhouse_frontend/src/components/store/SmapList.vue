<template>
  <div cols="12" ms="12">
    <v-row id="map">
      <v-btn id="btnRoadView" @click="showRoadView">로드뷰</v-btn>
      <div id="roadView"></div>
      <div class="category">
        <ul>
          <li id="allMenu" @click="changeMarker('all')">
            <span class="ico_comm ico_coffee"></span>
            전체보기
          </li>
          <li id="coffeeMenu" @click="changeMarker('coffee')">
            <span class="ico_comm ico_coffee"></span>
            커피숍
          </li>
          <li id="restaurantMenu" @click="changeMarker('restaurant')">
            <span class="ico_comm2"></span>
            음식점
          </li>
          <li id="storeMenu" @click="changeMarker('store')">
            <span class="ico_comm ico_store"></span>
            편의점
          </li>
          <li id="carparkMenu" @click="changeMarker('carpark')">
            <span class="ico_comm ico_carpark"></span>
            주차장
          </li>
        </ul>
      </div>
    </v-row>
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
        @click="setOverlayMapTypeId"
        class="mr-2"
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
  name: "SmapList",

  data: () => ({
    markers: [],
    infowindow: null,
    marker: null,
    curlat: 0,
    curlng: 0,
    roadView: false,
    rvContainer: null,
    rv: null,
    rvClient: null,
    rvMarker: null,
    map: null,
    terrain: null,
    traffic: null,
    bicycle: null,
    chkTraffic: false,
    chkBicycle: false,
    chkTerrain: false,
    markerImageSrc: "",
    coffeeMarkers: [], // 커피숍 마커 객체를 가지고 있을 배열입니다
    storeMarkers: [], // 편의점 마커 객체를 가지고 있을 배열입니다
    carparkMarkers: [], // 주차장 마커 객체를 가지고 있을 배열입니다
    coffeePositions: [],
    storePositions: [],
    carparkPositions: [],
    resMarkers: [],
    resPositions: [],
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
    ...mapState(houseStore, ["stores"]),
  },
  watch: {
    stores: function () {
      this.displayMarkers(this.stores);
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
        this.displayMarkers(this.stores);
      });
      this.markerImageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png";
      this.terrain = kakao.maps.MapTypeId.TERRAIN;
      this.traffic = kakao.maps.MapTypeId.TRAFFIC;
      this.bicycle = kakao.maps.MapTypeId.BICYCLE;
      this.useDistrict = kakao.maps.MapTypeId.USE_DISTRICT;
    }, 500);
  },
  methods: {
    ...mapActions(houseStore, ["detailStore"]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.curlat, this.curlng),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
      // var marker = new kakao.maps.Marker({
      //   position: this.map.getCenter(),
      // });
      // marker.setMap(this.map);
      // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
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
      this.coffeePositions = [];
      this.storePositions = [];
      this.carparkPositions = [];
      this.resPositions = [];
      this.markers = [];
      this.coffeeMarkers = [];
      this.carparkMarkers = [];
      this.storeMarkers = [];
      this.resMarkers = [];
      const positions = markerPositions.map(
        (store) => new kakao.maps.LatLng(store.lat, store.lng),
      );
      var idx = 0;
      var idx2 = 0;
      var idx3 = 0;
      var idx4 = 0;
      markerPositions.map((store) => {
        if (store.type === "커피전문점/카페/다방") {
          // console.log(store.type);
          this.coffeePositions[idx++] = new kakao.maps.LatLng(
            store.lat,
            store.lng,
          );
          // console.log("카페" + store.lat, store.lng);
        }
        if (store.type === "주차장운영") {
          this.carparkPositions[idx2++] = new kakao.maps.LatLng(
            store.lat,
            store.lng,
          );
        }
        if (store.type === "편의점") {
          this.storePositions[idx3++] = new kakao.maps.LatLng(
            store.lat,
            store.lng,
          );
        }
        if (
          store.type === "후라이드/양념치킨" ||
          store.type === "한식/백반/한정식" ||
          store.type === "피자전문" ||
          store.type === "중국음식/중국집" ||
          store.type === "족발/보쌈전문" ||
          store.type === "우동전문점" ||
          store.type === "국수/만두/칼국수" ||
          store.type === "냉면집" ||
          store.type === "라면김밥분식" ||
          store.type === "호프/맥주"
        ) {
          this.resPositions[idx4++] = new kakao.maps.LatLng(
            store.lat,
            store.lng,
          );
        }
      });
      // console.log("확인: " + this.coffeePositions);
      this.createCoffeeMarkers();
      this.createStoreMarkers();
      this.createCarparkMarkers();
      this.createResMarkers();

      this.changeMarker("all");
      const informations = markerPositions.map((store) => ({
        position: new kakao.maps.LatLng(store.lat, store.lng),
        content: `<div style="padding: 10px;text-align:center;width: 150px"><h5>${store.sname}</h5></div>`,
        store: store,
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
          this.moveToDetail(informations[i].store),
        );
        this.markers.push(marker);
      }
      // console.log(this.markers);
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
        self.detailStore(house);
        if (self.$route.path !== "/store/sdetail")
          self.$router.push("/store/sdetail");
      };
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
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
    setOverlayMapTypeId() {
      this.map.removeOverlayMapTypeId(this.traffic);
      this.map.removeOverlayMapTypeId(this.bicycle);
      this.map.removeOverlayMapTypeId(this.terrain);
      if (this.chkTraffic) this.map.addOverlayMapTypeId(this.traffic);
      if (this.chkBicycle) this.map.addOverlayMapTypeId(this.bicycle);
      if (this.chkTerrain) this.map.addOverlayMapTypeId(this.terrain);
    },
    createMarkerImage(src, size, options) {
      var markerImage = new kakao.maps.MarkerImage(src, size, options);
      return markerImage;
    },
    createMarker(position, image) {
      var marker = new kakao.maps.Marker({
        position: position,
        image: image,
      });

      return marker;
    },
    // 커피숍 마커를 생성하고 커피숍 마커 배열에 추가하는 함수입니다
    createCoffeeMarkers() {
      for (var i = 0; i < this.coffeePositions.length; i++) {
        var imageSize = new kakao.maps.Size(22, 26),
          imageOptions = {
            spriteOrigin: new kakao.maps.Point(10, 0),
            spriteSize: new kakao.maps.Size(36, 98),
          };

        // 마커이미지와 마커를 생성합니다
        var markerImage = this.createMarkerImage(
            this.markerImageSrc,
            imageSize,
            imageOptions,
          ),
          marker = this.createMarker(this.coffeePositions[i], markerImage);

        // 생성된 마커를 커피숍 마커 배열에 추가합니다
        this.coffeeMarkers.push(marker);
      }
    },
    setCoffeeMarkers(map) {
      // if (this.coffeeMarkers.length > 0) {
      //   this.coffeeMarkers.forEach(this.coffeeMarkers.setMap(map));
      // }
      // console.log(this.coffeeMarkers);
      for (var i = 0; i < this.coffeeMarkers.length; i++) {
        this.coffeeMarkers[i].setMap(map);
        // console.log(this.coffeeMarkers[i]);
      }
    },
    createStoreMarkers() {
      for (var i = 0; i < this.storePositions.length; i++) {
        var imageSize = new kakao.maps.Size(22, 26),
          imageOptions = {
            spriteOrigin: new kakao.maps.Point(10, 36),
            spriteSize: new kakao.maps.Size(36, 98),
          };

        // 마커이미지와 마커를 생성합니다
        var markerImage = this.createMarkerImage(
            this.markerImageSrc,
            imageSize,
            imageOptions,
          ),
          marker = this.createMarker(this.storePositions[i], markerImage);

        // 생성된 마커를 편의점 마커 배열에 추가합니다
        this.storeMarkers.push(marker);
      }
    },
    setStoreMarkers(map) {
      for (var i = 0; i < this.storeMarkers.length; i++) {
        this.storeMarkers[i].setMap(map);
      }
    },
    createCarparkMarkers() {
      for (var i = 0; i < this.carparkPositions.length; i++) {
        var imageSize = new kakao.maps.Size(22, 26),
          imageOptions = {
            spriteOrigin: new kakao.maps.Point(10, 72),
            spriteSize: new kakao.maps.Size(36, 98),
          };

        // 마커이미지와 마커를 생성합니다
        var markerImage = this.createMarkerImage(
            this.markerImageSrc,
            imageSize,
            imageOptions,
          ),
          marker = this.createMarker(this.carparkPositions[i], markerImage);

        // 생성된 마커를 주차장 마커 배열에 추가합니다
        this.carparkMarkers.push(marker);
      }
    },
    setCarparkMarkers(map) {
      for (var i = 0; i < this.carparkMarkers.length; i++) {
        this.carparkMarkers[i].setMap(map);
      }
    },
    // 음식점 마커를 생성하고 음식점 마커 배열에 추가하는 함수입니다
    createResMarkers() {
      for (var i = 0; i < this.resPositions.length; i++) {
        var imageSize = new kakao.maps.Size(30, 30);
        // imageOptions = {
        //   spriteOrigin: new kakao.maps.Point(10, 0),
        //   spriteSize: new kakao.maps.Size(36, 98),
        // };

        // 마커이미지와 마커를 생성합니다
        var markerImage = this.createMarkerImage(
            require("../../assets/restaurant.png"),
            // this.markerImageSrc,
            imageSize,
            // imageOptions,
          ),
          marker = this.createMarker(this.resPositions[i], markerImage);

        // 생성된 마커를 음식점 마커 배열에 추가합니다
        this.resMarkers.push(marker);
      }
    },
    setResMarkers(map) {
      // console.log(this.coffeeMarkers);
      for (var i = 0; i < this.resMarkers.length; i++) {
        this.resMarkers[i].setMap(map);
        // console.log(this.resMarkers[i]);
      }
    },
    changeMarker(type) {
      var allMenu = document.getElementById("allMenu");
      var coffeeMenu = document.getElementById("coffeeMenu");
      var storeMenu = document.getElementById("storeMenu");
      var carparkMenu = document.getElementById("carparkMenu");
      var resMenu = document.getElementById("restaurantMenu");
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }
      this.markers = [];
      // this.displayMarkers(this.stores);
      // 커피숍 카테고리가 클릭됐을 때
      if (type === "coffee") {
        // 커피숍 카테고리를 선택된 스타일로 변경하고
        allMenu.className = "";
        coffeeMenu.className = "menu_selected";

        // 편의점과 주차장 카테고리는 선택되지 않은 스타일로 바꿉니다
        storeMenu.className = "";
        carparkMenu.className = "";
        resMenu.className = "";

        // 커피숍 마커들만 지도에 표시하도록 설정합니다
        // this.markers = [];
        this.setCoffeeMarkers(this.map);
        this.setStoreMarkers(null);
        this.setCarparkMarkers(null);
        this.setResMarkers(null);
      } else if (type === "store") {
        // 편의점 카테고리가 클릭됐을 때

        // 편의점 카테고리를 선택된 스타일로 변경하고
        allMenu.className = "";
        coffeeMenu.className = "";
        storeMenu.className = "menu_selected";
        carparkMenu.className = "";
        resMenu.className = "";

        // 편의점 마커들만 지도에 표시하도록 설정합니다
        this.setCoffeeMarkers(null);
        this.setStoreMarkers(this.map);
        this.setCarparkMarkers(null);
        this.setResMarkers(null);
      } else if (type === "carpark") {
        // 주차장 카테고리가 클릭됐을 때

        // 주차장 카테고리를 선택된 스타일로 변경하고
        allMenu.className = "";
        coffeeMenu.className = "";
        storeMenu.className = "";
        carparkMenu.className = "menu_selected";
        resMenu.className = "";

        // 주차장 마커들만 지도에 표시하도록 설정합니다
        this.setCoffeeMarkers(null);
        this.setStoreMarkers(null);
        this.setCarparkMarkers(this.map);
        this.setResMarkers(null);
      } else if (type === "restaurant") {
        // 주차장 카테고리가 클릭됐을 때

        // 주차장 카테고리를 선택된 스타일로 변경하고
        allMenu.className = "";
        coffeeMenu.className = "";
        storeMenu.className = "";
        carparkMenu.className = "";
        resMenu.className = "menu_selected";

        // 주차장 마커들만 지도에 표시하도록 설정합니다
        this.setCoffeeMarkers(null);
        this.setStoreMarkers(null);
        this.setCarparkMarkers(null);
        this.setResMarkers(this.map);
      } else if (type === "all") {
        allMenu.className = "menu_selected";
        coffeeMenu.className = "";
        storeMenu.className = "";
        carparkMenu.className = "";
        resMenu.className = "";
        // this.displayMarkers(this.map);
        const positions = this.stores.map(
          (store) => new kakao.maps.LatLng(store.lat, store.lng),
        );
        const informations = this.stores.map((store) => ({
          position: new kakao.maps.LatLng(store.lat, store.lng),
          content: `<div style="padding: 10px;text-align:center;width: 150px"><h5>${store.sname}</h5></div>`,
          store: store,
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
            this.moveToDetail(informations[i].store),
          );
          this.markers.push(marker);
        }
        // console.log(this.markers);
        if (positions.length > 0) {
          const bounds = positions.reduce(
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds(),
          );

          this.map.setBounds(bounds);
        }
        this.setCoffeeMarkers(null);
        this.setStoreMarkers(null);
        this.setCarparkMarkers(null);
        this.setResMarkers(null);
      }
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
#mapwrap {
  position: relative;
  overflow: hidden;
}
.category,
.category * {
  margin: 0;
  padding: 0;
  color: #000;
}
.category {
  position: absolute;
  overflow: hidden;
  bottom: 10px;
  right: 10px;
  width: 250px;
  height: 50px;
  z-index: 10;
  border: 1px solid black;
  font-family: "Malgun Gothic", "맑은 고딕", sans-serif;
  font-size: 12px;
  text-align: center;
  background-color: #fff;
}
.category .menu_selected {
  background: #ff5f4a;
  color: #fff;
  border-left: 1px solid #915b2f;
  border-right: 1px solid #915b2f;
  margin: 0 -1px;
}
.category li {
  list-style: none;
  float: left;
  width: 50px;
  height: 45px;
  padding-top: 5px;
  cursor: pointer;
}
.category .ico_comm {
  display: block;
  margin: 0 auto 2px;
  width: 22px;
  height: 26px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/category.png")
    no-repeat;
}
.category .ico_comm2 {
  display: block;
  margin: 0 auto 2px;
  width: 22px;
  height: 26px;
  background: url("../../assets/restaurant.png") no-repeat;
}
.category .ico_coffee {
  background-position: -10px 0;
}
.category .ico_store {
  background-position: -10px -36px;
}
.category .ico_carpark {
  background-position: -10px -72px;
}
.category .ico_restaurant {
  background-position: -10px -72px;
}
</style>
