import { apiInstance } from "./index.js";

const api = apiInstance();

async function findInterestHouse(userid, success, fail) {
  await api
    .get(`/interestHouse/apt/${userid}`)
    .then(success)
    .catch(fail);
}
async function findInterestOffice(userid, success, fail) {
  await api
    .get(`/interestHouse/office/${userid}`)
    .then(success)
    .catch(fail);
}
async function addInterest(data, success, fail) {
  await api
    .post(`/interestHouse`, JSON.stringify(data))
    .then(success)
    .catch(fail);
}
async function deleteInterest(num, success, fail) {
  await api
    .delete(`/interestHouse/${num}`)
    .then(success)
    .catch(fail);
}
export { findInterestHouse, addInterest, deleteInterest, findInterestOffice };
