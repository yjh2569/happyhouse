<template>
  <v-container class="container">
    <v-card
      id="homeText"
      outlined
      style="background: linear-gradient(#e0c3fc66 0%, #8ec5fc66 100%)"
    >
      <v-card-title id="homeTitle" class="ma-5 white--text"
        >HappyHouse</v-card-title
      >
      <v-card-subtitle id="homeSubtitle" class="ma-5 white--text"
        >HappyHouse에 오신 것을 환영합니다.</v-card-subtitle
      >
    </v-card>
    <v-row v-for="(picture, index) in pictures" :key="index">
      <v-spacer></v-spacer>
      <v-col cols="3">
        <v-card class="mx-auto my-5 homecard" outlined color="transparent">
          <router-link :to="{ name: picture.link }">
            <v-img width="500" :src="picture.path"></v-img>
          </router-link>
        </v-card>
      </v-col>
      <v-col cols="2"></v-col>
    </v-row>
    <v-row>
      <v-spacer></v-spacer>
      <v-col cols="3">
        <v-card class="mx-auto homecard" outlined color="transparent">
          <v-img contain width="500" src="@/assets/test11.png"></v-img>
        </v-card>
      </v-col>
      <v-col cols="2"></v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "HelloWorld",

  data() {
    return {
      scrolledToBottom: true,
      pictures: [
        { link: "home", path: require("@/assets/test12.png") },
        { link: "map", path: require("@/assets/test6.png") },
        { link: "map", path: require("@/assets/test5.png") },
        { link: "map", path: require("@/assets/test8.png") },
        { link: "QnA", path: require("@/assets/test9.png") },
        { link: "chart", path: require("@/assets/test10.png") },
        { link: "home", path: require("@/assets/test12.png") },
      ],
    };
  },
  methods: {
    scroll() {
      window.onscroll = function () {
        console.log(window.scrollY);
        let bottomOfWindow =
          Math.max(
            window.pageYOffset,
            document.documentElement.scrollTop,
            document.body.scrollTop,
          ) +
            window.innerHeight ===
          document.documentElement.offsetHeight;

        if (bottomOfWindow) {
          this.scrolledToBottom = true; // replace it with your code
          window.scroll(0, 11);
        } else if (window.scrollY < 10) {
          this.scrolledToBottom = false;
          window.scroll(0, document.documentElement.offsetHeight - 100);
        }
        if (window.scrollY >= 0 && window.scrollY < 210) {
          document.getElementById("homeTitle").innerText = "HappyHouse";
          document.getElementById("homeSubtitle").innerText =
            "HappyHouse에 오신 것을 환영합니다.";
        } else if (window.scrollY >= 210 && window.scrollY < 920) {
          document.getElementById("homeTitle").innerText = "Map";
          document.getElementById("homeSubtitle").innerText =
            "아파트 위치 정보를 지도 상에서 확인하실 수 있습니다.";
        } else if (window.scrollY >= 920 && window.scrollY < 1630) {
          document.getElementById("homeTitle").innerText = "Apartment List";
          document.getElementById("homeSubtitle").innerText =
            "아파트 매매 내역 및 차트를 확인하실 수 있습니다.";
        } else if (window.scrollY >= 1630 && window.scrollY < 2340) {
          document.getElementById("homeTitle").innerText = "Review";
          document.getElementById("homeSubtitle").innerText =
            "아파트에 대한 리뷰를 확인하거나 작성하실 수 있습니다.";
        } else if (window.scrollY >= 2340 && window.scrollY < 3050) {
          document.getElementById("homeTitle").innerText = "Q&A";
          document.getElementById("homeSubtitle").innerText =
            "Q&A 게시판을 통해 아파트 관련 정보를 공유하실 수 있습니다.";
        } else if (window.scrollY >= 3050 && window.scrollY < 3760) {
          document.getElementById("homeTitle").innerText = "Chart";
          document.getElementById("homeSubtitle").innerText =
            "차트를 통해 각 지역의 아파트 정보를 한 눈에 파악하실 수 있습니다.";
        } else if (window.scrollY >= 3760) {
          document.getElementById("homeTitle").innerText = "HappyHouse";
          document.getElementById("homeSubtitle").innerText =
            "HappyHouse에 오신 것을 환영합니다.";
        }
      };
    },
  },
  mounted() {
    this.scroll();
  },
  destroyed() {
    window.onscroll = () => {};
  },
};
</script>

<style scoped>
#homeText {
  position: fixed;
  top: 40%;
  left: 20%;
  animation-name: fadein;
  animation-duration: 1s;
}
#homeTitle {
  font-size: 3.5rem;
  animation-name: fadein;
  animation-duration: 1s;
}
#homeSubtitle {
  font-size: 1.5rem;
  animation-name: fadein;
  animation-duration: 1s;
}
.homecard {
  animation-name: fadein;
  animation-duration: 1s;
}
.container {
  background-image: url("../assets/house.gif");
  width: 100%;
  height: 100%;
  max-width: 100% !important;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
}

@keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
