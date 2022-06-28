<template>
  <v-container class="ma-5">
    <v-row class="mx-auto">
      <v-spacer></v-spacer>
      <v-col cols="3">
        <v-select
          v-model="sidoCode"
          label="시/도"
          :items="sidos"
          @change="sidoSelect"
        >
        </v-select>
      </v-col>
      <v-col cols="3">
        <v-select
          v-model="gugunCode"
          :label="gugunLabel"
          :items="guguns"
          @change="gugunSelect"
        >
        </v-select>
      </v-col>
      <v-col cols="3">
        <v-select
          v-model="dongCode"
          :label="dongLabel"
          :items="dongs"
          @change="showChart"
        >
        </v-select>
      </v-col>
      <v-spacer></v-spacer>
    </v-row>
  </v-container>
</template>

<script>
import { sidoList, gugunList, dongList } from "@/api/house.js";

export default {
  name: "ChartSearchBar",
  data() {
    return {
      sidoCode: "",
      gugunCode: "",
      dongCode: "",
      sidos: [],
      guguns: [],
      dongs: [],
      gugunLabel: "",
      dongLabel: "",
    };
  },
  created() {
    sidoList(
      ({ data }) => {
        data.forEach((sido) => {
          this.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
        });
      },
      (error) => {
        console.log(error);
      },
    );
  },
  methods: {
    sidoSelect() {
      this.gugunLabel = "시/구/군";
      this.dongLabel = "";
      this.guguns = [];
      gugunList(
        {
          sido: this.sidoCode,
        },
        ({ data }) => {
          data.forEach((gugun) => {
            this.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
          });
        },
        (error) => {
          console.log(error);
        },
      );
    },
    gugunSelect() {
      this.dongLabel = "읍/면/동/리";
      this.dongs = [];
      dongList(
        {
          gugun: this.gugunCode,
        },
        ({ data }) => {
          data.forEach((dong) => {
            this.dongs.push({ value: dong.dongCode, text: dong.dongName });
          });
        },
        (error) => {
          console.log(error);
        },
      );
    },
    showChart() {
      this.$emit("showChart", { searchKey: "dongCode", word: this.dongCode });
    },
  },
};
</script>

<style></style>
