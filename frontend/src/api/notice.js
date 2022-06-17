import { apiInstance } from "./index.js";

const api = apiInstance();

function listNotice(success, fail) {
  api
    .get(`/notice`)
    .then(success)
    .catch(fail);
}

function writeNotice(notice, success, fail) {
  api
    .post(`/notice`, JSON.stringify(notice))
    .then(success)
    .catch(fail);
}

function getNotice(noticeno, success, fail) {
  api
    .get(`/notice/${noticeno}`)
    .then(success)
    .catch(fail);
}

function modifyNotice(notice, success, fail) {
  api
    .put(`/notice/${notice.num}`, JSON.stringify(notice))
    .then(success)
    .catch(fail);
}
function addCount(num, success, fail) {
  api
    .patch(`/notice/${num}`)
    .then(success)
    .catch(fail);
}

function deleteNotice(noticeno, success, fail) {
  api
    .delete(`/notice/${noticeno}`)
    .then(success)
    .catch(fail);
}

export {
  listNotice,
  writeNotice,
  getNotice,
  modifyNotice,
  deleteNotice,
  addCount,
};
