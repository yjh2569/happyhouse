<template>
  <v-row>
    <v-col cols="12" md="5">
      <house-detail-card></house-detail-card>
    </v-col>
    <v-col cols="12" md="7">
      <review-write></review-write>
      <review-list></review-list>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";
import HouseDetailCard from "@/components/house/HouseDetailCard.vue";
import ReviewList from "@/components/review/ReviewList.vue";
import ReviewWrite from "../review/ReviewWrite.vue";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  // data: () => ({}),
  components: {
    HouseDetailCard,
    ReviewList,
    ReviewWrite,
  },
  computed: {
    ...mapState(houseStore, ["house"]),
  },
};
</script>

<style></style>
