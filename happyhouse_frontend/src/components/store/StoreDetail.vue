<template>
  <v-row>
    <v-col cols="12" md="5">
      <store-detail-card></store-detail-card>
    </v-col>
    <v-col cols="12" md="7">
      <sreview-write></sreview-write>
      <sreview-list></sreview-list>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";
import StoreDetailCard from "@/components/store/StoreDetailCard.vue";
import SreviewList from "../review/SreviewList.vue";
import SreviewWrite from "../review/SreviewWrite.vue";

const houseStore = "houseStore";

export default {
  name: "StoreDetail",
  data: () => ({}),
  components: {
    StoreDetailCard,
    SreviewList,
    SreviewWrite,
  },
  computed: {
    ...mapState(houseStore, ["store"]),
  },
};
</script>

<style></style>
