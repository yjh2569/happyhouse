import { apiInstance } from "./index.js";

const api = apiInstance();

function listQna(param, success, fail) {
  api.get(`/qna`, { params: param }).then(success).catch(fail);
}

function getTotalCount(param, success, fail) {
  api.get(`/qna/count`, { params: param }).then(success).catch(fail);
}

function writeQna(article, success, fail) {
  api.post(`/qna`, JSON.stringify(article)).then(success).catch(fail);
}

function getQna(no, success, fail) {
  api.get(`/qna/${no}`).then(success).catch(fail);
}

function modifyQna(article, success, fail) {
  api.put(`/qna`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteQna(no, success, fail) {
  api.delete(`/qna/${no}`).then(success).catch(fail);
}

export { listQna, getTotalCount, writeQna, getQna, modifyQna, deleteQna };
