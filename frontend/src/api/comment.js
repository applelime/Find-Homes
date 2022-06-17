import { apiInstance } from "./index.js";

const api = apiInstance();

function listComment(boardNum, success, fail) {
  api
    .get(`/comment/${boardNum}`)
    .then(success)
    .catch(fail);
}

function writeComment(comment, success, fail) {
  api
    .post(`/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

function modifyComment(comment, success, fail) {
  api
    .put(`/comment/modify/${comment.num}`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

function deleteComment(num, success, fail) {
  api
    .delete(`/comment/delete/${num}`)
    .then(success)
    .catch(fail);
}

export { listComment, writeComment, modifyComment, deleteComment };
