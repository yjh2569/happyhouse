import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

function houseRankList(params, success, fail) {
  api.get(`/map/aptrank`, { params: params }).then(success).catch(fail);
}

function houseList(params, success, fail) {
  api.get(`/map/apt`, { params: params }).then(success).catch(fail);
}

function getTotalCount(params, success, fail) {
  api.get(`/map/count`, { params: params }).then(success).catch(fail);
}

function storeList(params, success, fail) {
  api.get(`/map/store`, { params: params }).then(success).catch(fail);
}

function sgetTotalCount(params, success, fail) {
  api.get(`/map/scount`, { params: params }).then(success).catch(fail);
}

export {
  sidoList,
  gugunList,
  dongList,
  houseRankList,
  houseList,
  storeList,
  getTotalCount,
  sgetTotalCount,
};
