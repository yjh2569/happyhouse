<template>
  <v-container id="view">
    <link
      href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet"
    />
    <v-row>
      <v-col cols="4">
        <house-search-bar @showChart="showChart"></house-search-bar>
        <house-list :searchKey="searchKey" :word="word"></house-list>
        <v-card flat class="px-auto py-auto">
          <v-card-text>
            <v-row align="center" justify="center">
              <v-btn-toggle
                v-model="toggle_exclusive"
                mandatory
                color="rgba(56, 161, 171,1)"
              >
                <v-btn
                  @click="
                    [
                      (rp = true),
                      (ar = false),
                      (by = false),
                      (di = false),
                      showChart({ searchKey, word }),
                    ]
                  "
                  color="white"
                >
                  <v-icon size="medium">local_atm</v-icon>
                  실거래순
                </v-btn>
                <v-btn
                  @click="
                    [
                      (rp = false),
                      (ar = true),
                      (by = false),
                      (di = false),
                      showChart({ searchKey, word }),
                    ]
                  "
                  color="white"
                >
                  <v-icon size="medium">apartment</v-icon>
                  면적순
                </v-btn>
                <v-btn
                  @click="
                    [
                      (rp = false),
                      (ar = false),
                      (by = true),
                      (di = false),
                      showChart({ searchKey, word }),
                    ]
                  "
                  color="white"
                >
                  <v-icon size="medium">build</v-icon>
                  건축년도순
                </v-btn>
                <v-btn
                  @click="
                    [
                      (rp = false),
                      (ar = false),
                      (by = false),
                      (di = true),
                      showChart({ searchKey, word }),
                    ]
                  "
                  color="white"
                >
                  <v-icon size="medium">social_distance</v-icon>
                  거리순
                </v-btn>
              </v-btn-toggle>
            </v-row>
          </v-card-text>
        </v-card>
        <chart-item
          rankKey="recentPrice"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
          v-show="rp"
          v-model="rp"
        />
        <chart-item
          rankKey="area"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
          v-show="ar"
          v-model="ar"
        />
        <chart-item
          rankKey="buildYear"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
          v-show="by"
          v-model="by"
        />
        <chart-item
          rankKey="dist"
          :lat="lat"
          :lng="lng"
          :searchKey="searchKey"
          :word="word"
          v-show="di"
          v-model="di"
        />
      </v-col>
      <v-col id="maplist" cols="8">
        <router-view></router-view>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import ChartItem from "@/components/chart/ChartItem.vue";

export default {
  name: "AptList",

  components: { HouseSearchBar, HouseList, ChartItem },
  data: () => ({
    searchKey: "",
    word: "",
    lat: 0,
    lng: 0,
    rp: true,
    ar: false,
    by: false,
    di: false,
    toggle_exclusive: undefined,
  }),
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

<style scoped>
#view {
  margin-top: 4%;
}
</style>
