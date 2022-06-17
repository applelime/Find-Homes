import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/user/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api
    .get(`/user/info/${userid}`)
    .then(success)
    .catch(fail);
}

function deleteUser(userid, success, fail) {
  api
    .delete(`user/${userid}`)
    .then(success)
    .catch(fail);
}
function register(user, success, fail) {
  api
    .post(`/user`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}
function update(user, success, fail) {
  api
    .put(`/user`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}
function getUsers(success, fail) {
  api
    .get(`user`)
    .then(success)
    .catch(fail);
}
function idCheck(userid, success, fail) {
  api
    .get(`user/idcheck/${userid}`)
    .then(success)
    .catch(fail);
}
function findPw(user, success, fail) {
  api
    .get(`user/findpw`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

export {
  login,
  findById,
  register,
  deleteUser,
  idCheck,
  update,
  getUsers,
  findPw,
};
