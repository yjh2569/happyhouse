<template>
  <div cols="12" md="12" v-if="stores">
    <!-- <v-data-table :headers="headers" :items="stores" :items-per-page="pp">
      <template v-slot:item="{ item, index }">
        <tr @click="selectStore(item)">
          <td>{{ index + 1 }}</td>
          <td>{{ item.sname }}</td>
          <td>{{ item.type }}</td>
          <td>{{ item.dongName }}</td>
          <td>{{ item.dist }}km</td>
        </tr>
      </template>
    </v-data-table> -->
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">번호</th>
            <th class="text-left">이름</th>
            <th class="text-left">종류</th>
            <th class="text-left">법정동</th>
            <th class="text-left">거리</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in stores"
            :key="item.scode"
            @click="selectStore(item)"
            style="cursor: pointer"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ item.sname }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.dongName }}</td>
            <td>{{ item.dist }}km</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <store-pagination-content
      :searchKey="searchKey"
      :word="word"
      @pagechanged="pagechanged"
    ></store-pagination-content>
  </div>
</template>

<script>
// import HouseListItem from "@/components/house/HouseListItem.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import StorePaginationContent from "./StorePaginationContent.vue";

const houseStore = "houseStore";

export default {
  name: "StoreList",
  props: {
    searchKey: String,
    word: String,
  },
  components: {
    StorePaginationContent,
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
      this.CLEAR_STORE_LIST();
    });
  },
  computed: {
    ...mapState(houseStore, ["stores"]),
  },
  methods: {
    ...mapActions(houseStore, ["detailStore", "getStoreList"]),
    ...mapMutations(houseStore, ["CLEAR_STORE_LIST"]),
    selectStore(data) {
      // console.log("click" + data.aptCode);
      this.detailStore(data);
      if (this.$route.path !== "/store/sdetail")
        this.$router.push("/store/sdetail");
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
        this.getStoreList(param);
      }
    },
  },
};
</script>

<style></style>
