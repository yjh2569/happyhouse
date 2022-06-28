<template>
  <v-container id="view">
    <v-row>
      <v-col cols="4" md="4">
        <store-search-bar @searched="searched"></store-search-bar>
        <store-list :searchKey="searchKey" :word="word"></store-list>
        <!-- <a href="../map">apt</a> -->
      </v-col>
      <v-col id="maplist" cols="8" ms="12">
        <router-view></router-view>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import StoreSearchBar from "@/components/store/StoreSearchBar.vue";
import StoreList from "@/components/store/StoreList.vue";

export default {
  name: "StoreListView",

  components: { StoreSearchBar, StoreList },
  data: () => ({
    searchKey: "",
    word: "",
  }),
  methods: {
    searched({ searchKey, word }) {
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
