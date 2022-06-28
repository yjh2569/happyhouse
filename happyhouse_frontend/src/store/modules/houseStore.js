import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  storeList,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [],
    house: null,
    stores: [],
    store: null,
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "시/도" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "구/군" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = [];
    },
    CLEAR_STORE_LIST: (state) => {
      state.stores = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_STORE_LIST: (state, stores) => {
      state.stores = stores;
    },
    SET_DETAIL_STORE: (state, store) => {
      state.store = store;
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        },
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        },
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = {
        gugun: gugunCode,
      };
      dongList(
        params,
        ({ data }) => {
          //   console.log(commit);
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        },
      );
    },
    getHouseList: ({ commit }, { pg, key, word, lat, lng }) => {
      // console.log(dongCode);
      //   // vue cli enviroment variables 검색
      //   //.env.local file 생성.
      //   // 반드시 VUE_APP으로 시작해야 한다.
      //   const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      //   //   const SERVICE_KEY =
      //   //     "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      const params = {
        pg,
        key,
        word,
        lat,
        lng,
      };
      houseList(
        params,
        (response) => {
          console.log(response.data);
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        },
      );
    },
    getStoreList: ({ commit }, { pg, key, word, lat, lng }) => {
      const params = {
        pg,
        key,
        word,
        lat,
        lng,
      };
      storeList(
        params,
        (response) => {
          console.log(response.data);
          commit("SET_STORE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        },
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
    detailStore: ({ commit }, store) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_STORE", store);
    },
  },
};

export default houseStore;
