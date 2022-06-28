<template>
  <v-container class="bv-example-row mt-3 text-center">
    <v-row>
      <v-col>
        <chart-search-bar @showChart="showChart"></chart-search-bar>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6">
        <chart-item
          rankKey="recentPrice"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
        />
      </v-col>
      <v-col cols="6">
        <chart-item
          rankKey="area"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6">
        <chart-item
          rankKey="buildYear"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
        />
      </v-col>
      <v-col cols="6">
        <chart-item
          rankKey="dist"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import ChartSearchBar from "@/components/chart/ChartSearchBar.vue";
import ChartItem from "@/components/chart/ChartItem.vue";

export default {
  name: "ChartView",
  components: {
    ChartSearchBar,
    ChartItem,
  },
  data() {
    return {
      searchKey: "",
      word: "",
      lat: 0,
      lng: 0,
    };
  },
  created() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.lat = position.coords.latitude;
      this.lng = position.coords.longitude;
    });
  },
  methods: {
    showChart({ searchKey, word }) {
      this.searchKey = searchKey;
      this.word = word;
    },
  },
};
</script>
<style></style>
