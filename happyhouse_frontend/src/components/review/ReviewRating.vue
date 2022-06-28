<template>
  <!-- <v-simple-table class="mx-auto my-auto"> -->
  <v-container>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="0"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[0] }}명</v-col>
    </v-row>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="1"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[1] }}명</v-col>
    </v-row>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="2"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[2] }}명</v-col>
    </v-row>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="3"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[3] }}명</v-col>
    </v-row>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="4"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[4] }}명</v-col>
    </v-row>
    <v-row class="mx-auto my-auto">
      <v-col cols="9">
        <v-rating
          :value="5"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating
      ></v-col>
      <v-col cols="3">{{ ratings[5] }}명</v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { apiInstance } from "@/api/index";
const houseStore = "houseStore";

export default {
  components: {},
  name: "ReviewRating",

  data: () => ({
    reviews: [],
    rating: 0,
    ratings: [],
  }),
  created() {
    for (let i = 0; i <= 5; i++) {
      apiInstance()
        .get(`/review/rcount`, {
          params: {
            aptCode: this.house.aptCode,
            rating: i,
          },
        })
        .then(({ data }) => {
          // console.log(data);
          this.$set(this.ratings, i, data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
  computed: {
    ...mapState(houseStore, ["house"]),
  },
  watch: {
    reviews: function () {
      apiInstance()
        .get(`/review/list/${this.house.aptCode}`)
        .then(({ data }) => {
          // console.log(data);
          this.reviews = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    house: function () {
      for (let i = 0; i <= 5; i++) {
        apiInstance()
          .get(`/review/rcount`, {
            params: {
              aptCode: this.house.aptCode,
              rating: i,
            },
          })
          .then(({ data }) => {
            // console.log(data);
            this.$set(this.ratings, i, data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
  },
};
</script>

<style scoped></style>
