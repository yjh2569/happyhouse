<template>
  <v-container>
    <bar
      v-if="loaded"
      :chart-data="chartData"
      :chartOptions="options"
      :styles="myStyles"
    />
  </v-container>
</template>

<script>
import { Bar } from "vue-chartjs/legacy";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import { houseRankList } from "@/api/house.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
);

export default {
  name: "ChartItem",
  components: { Bar },
  props: {
    searchKey: String,
    word: String,
    rankKey: String,
    lat: Number,
    lng: Number,
  },
  data() {
    return {
      loaded: false,
      chartData: null,
      rawData: [],
      min: 0,
      max: 0,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            min: 0,
            max: 5000,
          },
        },
      },
    };
  },
  computed: {
    title() {
      if (this.rankKey == "recentPrice") {
        return "최근 거래 금액(억 원)";
      } else if (this.rankKey == "area") {
        return "면적(㎡)";
      } else if (this.rankKey == "buildYear") {
        return "건축년도(년)";
      } else if (this.rankKey == "dist") {
        return "거리(km)";
      }
      return "";
    },
    myStyles() {
      return {
        height: `320px`,
        position: "relative",
      };
    },
  },
  watch: {
    searchKey() {
      this.searchAndShow();
    },
    key() {
      this.searchAndShow();
    },
    word() {
      this.searchAndShow();
    },
  },
  methods: {
    searchAndShow() {
      console.log(this.searchKey);
      houseRankList(
        {
          key: this.searchKey,
          word: this.word,
          rankKey: this.rankKey,
          lat: this.lat,
          lng: this.lng,
        },
        ({ data }) => {
          this.min = 0;
          this.max = 0;
          this.chartData = {
            labels: [],
            datasets: [
              {
                label: this.title,
                data: [],
                backgroundColor: [
                  "rgba(255, 0, 0, 0.5)",
                  "rgba(255, 165, 0, 0.5)",
                  "rgba(255, 255, 0, 0.5)",
                  "rgba(0, 255, 0, 0.5)",
                  "rgba(0, 128, 0, 0.5)",
                  "rgba(110, 200, 251, 0.5)",
                  "rgba(4, 101, 248, 0.5)",
                  "rgba(0, 0, 255, 0.5)",
                  "rgba(75, 0, 130, 0.5)",
                  "rgba(238, 130, 238, 0.5)",
                  "rgba(0, 0, 0, 0.5)",
                ],
                borderColor: [
                  "rgba(255, 0, 0, 1)",
                  "rgba(255, 165, 0, 1)",
                  "rgba(255, 255, 0, 1)",
                  "rgba(0, 255, 0, 1)",
                  "rgba(0, 128, 0, 1)",
                  "rgba(110, 200, 251, 1)",
                  "rgba(4, 101, 248, 1)",
                  "rgba(0, 0, 255, 1)",
                  "rgba(75, 0, 130, 1)",
                  "rgba(238, 130, 238, 1)",
                  "rgba(0, 0, 0, 1)",
                ],
              },
            ],
          };
          data.forEach((apt) => {
            if (this.rankKey == "recentPrice") {
              let p = apt[this.rankKey];
              p = p.slice(0, -1);
              if (p.includes("억")) {
                let arr = p.split("억");
                if (arr.length > 1 && arr[1].trim().length > 0) {
                  apt[this.rankKey] =
                    parseInt(arr[0]) + parseInt(arr[1]) * 0.0001;
                } else {
                  apt[this.rankKey] = parseInt(arr[0]);
                }
              } else {
                apt[this.rankKey] = parseInt(apt[this.rankKey]) * 0.0001;
              }
            }
            this.chartData.labels.push(apt.aptName);
            if (this.max > 0 && this.min == 0) {
              this.min = apt[this.rankKey];
            } else if (this.max > 0 && this.min > 0) {
              this.min = Math.min(apt[this.rankKey], this.min);
            }
            this.max = Math.max(apt[this.rankKey], this.max);
            this.chartData.datasets[0].data.push(apt[this.rankKey]);
          });
          this.options.scales.y.min = Math.max(this.min - 10, 0);
          this.options.scales.y.max = this.max;
          this.loaded = true;
        },
        (error) => console.log(error),
      );
    },
  },
};
</script>

<style></style>
