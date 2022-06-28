import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "@/views/HomeView.vue";

import store from "@/store/index.js";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
    children: [
      {
        path: "singin",
        name: "signIn",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/MemberMyPage.vue"),
      },
    ],
  },
  {
    path: "/notice",
    name: "notice",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "noticeList",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "write",
        name: "noticeRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeRegister.vue"),
      },
      {
        path: "detail/:articleno",
        name: "noticeDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "noticeModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
    ],
  },
  {
    path: "/qna",
    name: "QnA",
    component: () => import("@/views/QnaView.vue"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnaList",
        component: () => import("@/components/qna/QnaList.vue"),
      },
      {
        path: "write",
        name: "QnaRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/qna/QnaRegister.vue"),
      },
      {
        path: "detail/:no",
        name: "QnaDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/qna/QnaDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "QnaModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/qna/QnaModify.vue"),
      },
    ],
  },
  {
    path: "/chart",
    name: "chart",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/ChartView.vue"),
  },
  {
    path: "/map",
    name: "map",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/AptListView.vue"),
    // components: {
    //   default: AptListView,
    //   apt: HouseList,
    //   store: StoreList,
    // },
    redirect: "/map/mshow",
    children: [
      {
        path: "detail",
        name: "detail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/house/HouseDetail.vue"),
      },
      {
        path: "mshow",
        name: "mshow",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/house/MapList.vue"),
      },
    ],
  },
  {
    path: "/store",
    name: "store",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/StoreListView.vue"),
    // components: {
    //   default: AptListView,
    //   apt: HouseList,
    //   store: StoreList,
    // },
    redirect: "/store/sshow",
    children: [
      {
        path: "sdetail",
        name: "sdetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/store/StoreDetail.vue"),
      },
      {
        path: "sshow",
        name: "sshow",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/store/SmapList.vue"),
      },
    ],
  },
  {
    path: "/review",
    name: "review",
    beforeEnter: onlyAuthUser,
    component: () => import("@/components/review/ReviewList.vue"),
    children: [
      {
        path: "modify",
        name: "modify/:no",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/review/ReviewUpdate.vue"),
      },
    ],
  },
  {
    path: "/news",
    name: "news",
    component: () => import("@/views/NewsView.vue"),
    redirect: "/news/newslist",
    children: [
      {
        path: "newslist",
        name: "newsList",
        component: () => import("@/components/news/NewsList.vue"),
      },
    ],
  },
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
