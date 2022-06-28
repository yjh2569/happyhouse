<template>
  <v-card class="mx-auto my-auto" max-width="374">
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-img height="250" src="@/assets/store.jpg"></v-img>

    <v-card-title>{{ store.sname }}</v-card-title>

    <v-card-text>
      <v-row align="center" class="mx-0">
        <v-rating
          :value="reviewavg"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating>
        <div class="grey--text ms-4">{{ reviewavg }}</div>
      </v-row>

      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1">분류</div>
        <div class="mx-auto">
          {{ store.type }}
        </div>
      </v-row>
      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1">위도</div>
        <div class="mx-auto">
          {{ store.lat }}
        </div>
      </v-row>
      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1">경도</div>
        <div class="mx-auto">
          {{ store.lng }}
        </div>
      </v-row>
      <v-row class="mx-auto">
        <v-divider></v-divider>
      </v-row>
      <v-row class="mx-auto">
        <v-card-title class="mx-auto">평점별 평가수</v-card-title>
      </v-row>
      <v-row class="mx-auto">
        <sreview-rating></sreview-rating>
      </v-row>
    </v-card-text>
    <v-divider class="mx-4"></v-divider>

    <!-- <v-card-title>Tonight's availability</v-card-title>

    <v-card-text>
      <v-chip-group
        v-model="selection"
        active-class="deep-purple accent-4 white--text"
        column
      >
        <v-chip>5:30PM</v-chip>

        <v-chip>7:30PM</v-chip>

        <v-chip>8:00PM</v-chip>

        <v-chip>9:00PM</v-chip>
      </v-chip-group>
    </v-card-text>
-->
    <v-card-actions>
      <v-btn color="deep-purple lighten-2" text @click="mshow">
        지도보기
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapState } from "vuex";
import SreviewRating from "../review/SreviewRating.vue";
import { apiInstance } from "@/api/index";
const houseStore = "houseStore";

export default {
  components: { SreviewRating },
  name: "StoreDetailCard",
  data: () => ({
    reviewavg: 0,
  }),
  computed: {
    ...mapState(houseStore, ["store", "stores"]),
  },
  watch: {
    store: function () {
      apiInstance()
        .get(`/sreview/avg/${this.store.scode}`)
        .then(({ data }) => {
          this.reviewavg = Math.round(data * 100) / 100.0;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    apiInstance()
      .get(`/sreview/avg/${this.store.scode}`)
      .then(({ data }) => {
        // console.log(data);
        this.reviewavg = Math.round(data * 100) / 100.0;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    mshow() {
      if (this.$route.path !== "/store") this.$router.push("/store");
    },
  },
};
</script>

<style></style>
