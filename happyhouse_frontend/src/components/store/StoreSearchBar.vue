<template>
  <v-container>
    <v-row align="center">
      <v-col class="d-flex" md="6">
        <v-select
          :items="sidos"
          label="시/도"
          @change="gugunList"
          v-model="sidoCode"
          solo
        ></v-select>
      </v-col>
      <v-col class="d-flex" md="3">
        <v-select
          :items="guguns"
          label="구/군"
          @change="dongList"
          v-model="gugunCode"
          solo
        ></v-select>
      </v-col>
      <v-col class="d-flex" md="3">
        <v-select
          :items="dongs"
          label="동"
          v-model="dongCode"
          @change="searchStoreByDongCode"
          solo
        ></v-select>
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col cols="12">
        <v-text-field
          type="text"
          class="ml-1 form-control"
          v-model="storeName"
          append-icon="mdi-magnify"
          label="상가 이름"
          single-line
          hide-details
          @keyup.enter="searchStoreByStoreName"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",

  components: {},
  data: () => ({
    sidoCode: null,
    gugunCode: null,
    dongCode: null,
    storeName: "",
    lat: 0,
    lng: 0,
  }),
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses", "stores"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.getSido();
    navigator.geolocation.getCurrentPosition((position) => {
      this.lat = position.coords.latitude;
      this.lng = position.coords.longitude;
    });
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
      "getStoreList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchStoreByDongCode() {
      //   if (this.gugunCode) this.getHouseList(this.gugunCode);
      if (this.dongCode) {
        this.getStoreList({
          pg: 1,
          key: "dongCode",
          word: this.dongCode,
          lat: this.lat,
          lng: this.lng,
        });
        this.searched("dongCode", this.dongCode);
      }
    },
    searchStoreByStoreName() {
      //   if (this.gugunCode) this.getHouseList(this.gugunCode);
      if (this.storeName) {
        this.getStoreList({
          pg: 1,
          key: "sname",
          word: this.storeName,
          lat: this.lat,
          lng: this.lng,
        });
        this.searched("sname", this.storeName);
      }
    },
    searched(key, word) {
      this.$emit("searched", { searchKey: key, word: word });
    },
  },
};
</script>
