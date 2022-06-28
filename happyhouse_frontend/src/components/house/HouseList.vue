<template>
  <div cols="12" md="12" v-if="houses">
    <!-- <v-data-table :headers="headers" :items="houses" :items-per-page="pp">
      <template v-slot:item="{ item, index }">
        <house-list-item
            v-for="(house, index) in houses"
            :key="index"
            :house="house"
          ></house-list-item>
        <tr @click="selectHouse(item)">
          <td>{{ index + 1 }}</td>
          <td>{{ item.aptName }}</td>
          <td>{{ item.dongName }}</td>
          <td>{{ item.recentPrice | priceFormat }}</td>
          <td>{{ item.dist }}km</td>
        </tr>
      </template>
    </v-data-table> -->
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">번호</th>
            <th class="text-left">아파트이름</th>
            <th class="text-left">법정동</th>
            <th class="text-left">실거래가</th>
            <th class="text-left">거리</th>
          </tr>
        </thead>
        <tbody>
          <house-list-item
            v-for="(item, index) in houses"
            :key="item.aptCode"
            @selectHouse="selectHouse"
            :item="item"
            :index="index"
          ></house-list-item>
          <!-- <tr
            
          >
            <td>{{ index + 1 }}</td>
            <td>{{ item.aptName }}</td>
            <td>{{ item.dongName }}</td>
            <td>{{ item.recentPrice | priceFormat }}</td>
            <td>{{ item.dist }}km</td>
          </tr> -->
        </tbody>
      </template>
    </v-simple-table>
    <house-pagination-content
      :searchKey="searchKey"
      :word="word"
      @pagechanged="pagechanged"
    ></house-pagination-content>
  </div>
</template>

<script>
// import HouseListItem from "@/components/house/HouseListItem.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import HousePaginationContent from "./HousePaginationContent.vue";
import HouseListItem from "@/components/house/HouseListItem.vue";
// import { apiInstance } from "@/api/index";

const houseStore = "houseStore";

export default {
  name: "HouseList",
  props: {
    searchKey: String,
    word: String,
  },
  components: {
    HousePaginationContent,
    HouseListItem,
  },
  data() {
    return {
      pp: 5,
      lat: 0,
      lng: 0,
    };

    // headers: [
    //   {
    //     text: "번호",
    //     align: "start",
    //     value: "aptCode",
    //   },
    //   { text: "아파트 이름", value: "aptName" },
    //   { text: "법정동", value: "dongName" },
    //   { text: "실거래가", value: "recentPrice" },
    //   { text: "거리", value: "dist" },
    // ],
  },
  created() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.lat = position.coords.latitude;
      this.lng = position.coords.longitude;
      this.CLEAR_HOUSE_LIST();
    });
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse", "getHouseList"]),
    ...mapMutations(houseStore, ["CLEAR_HOUSE_LIST"]),
    selectHouse(value) {
      // console.log("click" + value.target);
      this.detailHouse(value);
      if (this.$route.path !== "/map/detail") this.$router.push("/map/detail");
    },
    pagechanged(page) {
      if (this.word !== "") {
        let param = {
          pg: page,
          key: this.searchKey,
          word: this.word,
          lat: this.lat,
          lng: this.lng,
        };
        this.getHouseList(param);
      }
    },
  },
  filters: {
    priceFormat(value) {
      let price = parseInt(value);
      let result = "";
      if (price / 100000000 >= 1) {
        result = Math.floor(price / 100000000) + "억 ";
      }
      if (price % 100000000 > 0)
        result = result + (price % 100000000) / 10000 + "만 ";
      result += "원";
      return result;
    },
  },
};
</script>

<style></style>
