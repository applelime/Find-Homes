import { apiInstance } from "./index.js";

const api = apiInstance();

function listQnaBoard(success, fail) {
  api
    .get(`/qnaboard`)
    .then(success)
    .catch(fail);
}

function writeQnaBoard(qnaboard, success, fail) {
  api
    .post(`/qnaboard`, JSON.stringify(qnaboard))
    .then(success)
    .catch(fail);
}

function getQnaBoard(num, success, fail) {
  api
    .get(`/qnaboard/${num}`)
    .then(success)
    .catch(fail);
}

function modifyQnaBoard(qnaboard, success, fail) {
  api
    .put(`/qnaboard/${qnaboard.num}`, JSON.stringify(qnaboard))
    .then(success)
    .catch(fail);
}

function deleteQnaBoard(num, success, fail) {
  api
    .delete(`/qnaboard/${num}`)
    .then(success)
    .catch(fail);
}

function addQnaBoardCount(num, success, fail) {
  api
    .patch(`/qnaboard/${num}`)
    .then(success)
    .catch(fail);
}

export {
  listQnaBoard,
  writeQnaBoard,
  getQnaBoard,
  modifyQnaBoard,
  deleteQnaBoard,
  addQnaBoardCount,
};
