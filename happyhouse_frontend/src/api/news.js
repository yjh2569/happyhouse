import { apiInstance } from "./index.js";

const api = apiInstance();

function newsList(params, success, fail) {
  api.get(`/news`, { params: params }).then(success).catch(fail);
}

function newsCount(params, success, fail) {
  api.get(`/news/count`, { params: params }).then(success).catch(fail);
}

export { newsList, newsCount };
