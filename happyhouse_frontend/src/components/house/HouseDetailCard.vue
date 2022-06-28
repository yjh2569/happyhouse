<template>
  <v-card class="mx-auto my-auto" max-width="374">
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-img height="250" src="@/assets/apt.jpg"></v-img>

    <v-card-title>{{ house.aptName }}</v-card-title>

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
        <div class="my-4 text-subtitle-1" style="width: 150px">건축연도</div>
        <div class="mx-auto">
          {{ house.buildYear }}
        </div>
      </v-row>
      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1" style="width: 150px">법정동</div>
        <div class="mx-auto">
          {{ house.dongName }}
        </div>
      </v-row>
      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1" style="width: 150px">실거래가</div>
        <div class="mx-auto">
          {{ house.recentPrice | priceFormat }}
        </div>
      </v-row>
      <v-row align="center" class="mx-0">
        <div class="my-4 text-subtitle-1" style="width: 150px">거리</div>
        <div class="mx-auto">{{ house.dist }}km</div>
      </v-row>
      <v-row class="mx-auto">
        <v-divider></v-divider>
      </v-row>
      <v-row class="mx-auto">
        <v-card-title class="mx-auto">평점별 평가수</v-card-title>
      </v-row>
      <v-row class="mx-auto">
        <review-rating></review-rating>
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
import { apiInstance } from "@/api/index";
import ReviewRating from "../review/ReviewRating.vue";

const houseStore = "houseStore";

export default {
  name: "HouseDetailCard",
  components: { ReviewRating },
  data: () => ({
    reviewavg: 0,
  }),
  computed: {
    ...mapState(houseStore, ["house", "houses"]),
  },
  watch: {
    house: function () {
      apiInstance()
        .get(`/review/avg/${this.house.aptCode}`)
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
      .get(`/review/avg/${this.house.aptCode}`)
      .then(({ data }) => {
        console.log(data);
        this.reviewavg = Math.round(data * 100) / 100.0;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    mshow() {
      if (this.$route.path !== "/map") {
        this.$router.push("/map");
        this.$emit("display");
      }
    },
  },
  filters: {
    priceFormat(value) {
      let price = parseInt(value);
      let result = "";
      if (price / 100000000 > 1) {
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
