import { apiInstance } from "./index.js";

const api = apiInstance();

function listComment(qnano, success, fail) {
  api.get(`/comment/${qnano}`).then(success).catch(fail);
}

function writeComment(article, success, fail) {
  api.post(`/comment`, JSON.stringify(article)).then(success).catch(fail);
}

function getComment(commentid, success, fail) {
  api.get(`/comment/detail/${commentid}`).then(success).catch(fail);
}

function modifyComment(article, success, fail) {
  api.put(`/comment`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteComment(commentid, success, fail) {
  api.delete(`/comment/${commentid}`).then(success).catch(fail);
}

export { listComment, writeComment, getComment, modifyComment, deleteComment };
