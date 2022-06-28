<template>
  <v-app>
    <v-app-bar fixed color="accent-4" dense style="z-index: 4" id="happytitle">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <v-btn v-if="!userInfo" color="primary" link :to="{ name: 'signIn' }">
        로그인
      </v-btn>
      <h4 v-if="userInfo" class="white--text">
        {{ userInfo.username }}({{ userInfo.userid }})님 환영합니다.
      </h4>
      <v-menu left bottom v-if="userInfo">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list v-if="userInfo">
          <v-list-item link :to="{ name: 'mypage' }">
            <v-list-item-title>my page</v-list-item-title>
          </v-list-item>
          <v-list-item @click.prevent="onClickLogout">
            <v-list-item-title>logout</v-list-item-title>
          </v-list-item>
        </v-list>
        <v-list v-else>
          <v-list-item
            v-for="item in userItems"
            :key="item.title"
            link
            :to="{ name: item.path }"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-navigation-drawer app temporary v-model="drawer">
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title
              class="text-h6 d-flex align-center justify-center"
              >HappyHouse
              <v-img
                class="d-inline-block"
                src="@/assets/home.jpg"
                max-width="30"
              ></v-img
            ></v-list-item-title>
          </v-list-item-content>
          <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
        </v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          :to="{ name: item.path }"
          exact-path
          active-class="highlighted"
          :class="item.path === $route.path ? 'highlighted' : ''"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const memberStore = "memberStore";
export default {
  name: "App",

  data() {
    return {
      drawer: false,
      items: [
        { title: "Main", icon: "mdi-home", path: "home" },
        { title: "Notice", icon: "mdi-clipboard-alert", path: "noticeList" },
        { title: "Chart", icon: "mdi-chart-bar", path: "chart" },
        { title: "House", icon: "mdi-home-modern", path: "mshow" },
        { title: "Trade Hub", icon: "mdi-storefront", path: "sshow" },
        {
          title: "Q&A",
          icon: "mdi-frequently-asked-questions",
          path: "QnaList",
        },
        {
          title: "News",
          icon: "mdi-newspaper-variant-multiple-outline",
          path: "newsList",
        },
      ],
      userItems: [
        { title: "login", path: "signIn" },
        { title: "register", path: "signUp" },
      ],
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // console.log("memberStore : ", ms);
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>

<style>
a {
  text-decoration: none;
  color: black;
}

a:hover {
  text-decoration: none;
  font-weight: bold;
}

.v-application {
  font-family: "NanumBarunpenB", sans-serif !important;
}
@font-face {
  font-family: "NanumBarunpenB";
  src: url("assets/fonts/NanumBarunpenB.ttf") format("truetype");
  font-weight: 400;
}

#select:hover,
.highlighted {
  /* background: rgb(238, 174, 202); */
  /* background: radial-gradient(
    circle,
    rgba(238, 174, 202, 1) 0%,
    rgba(148, 187, 233, 1) 100%
  ); */
  background-image: radial-gradient(circle, #a1c4fd 0%, #c2e9fb 100%);
  color: white;
}

#happytitle {
  background-image: radial-gradient(circle, #e0c3fc 0%, #8ec5fc 100%);
}
</style>
