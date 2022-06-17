import jwt_decode from "jwt-decode";
import {
  login,
  deleteUser,
  findById,
  idCheck,
  register,
  update,
} from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isIdCheck: false,
  },
  getters: {
    checkUserInfo: function(state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    ID_CHECK_OK: (state, isIdOk) => {
      state.isIdCheck = isIdOk;
    },
    ID_CHECK_NOT_OK: (state, isIdOk) => {
      state.isIdCheck = isIdOk;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(user, (response) => {
        if (response.data.message === "success") {
          let token = response.data["access-token"];
          commit("SET_IS_LOGIN", true);
          commit("SET_IS_LOGIN_ERROR", false);
          sessionStorage.setItem("access-token", token);
        } else {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
        }
      });
    },
    getUserInfo({ commit, dispatch }, token) {
      let decode_token = jwt_decode(token);
      findById(decode_token.userid, (response) => {
        if (response.data.message === "success") {
          commit("SET_USER_INFO", response.data.userInfo);

          let apt = { userid: decode_token.userid, type: "apt" };
          dispatch("interestStore/interestAptList", apt, { root: true });
          let office = { userid: decode_token.userid, type: "office" };
          dispatch("interestStore/interestAptList", office, { root: true });
        } else {
          console.log("유저 정보 없음!!");
        }
      });
    },
    registerUser(state, user) {
      register(user);
    },
    idConfirm({ commit }, userid) {
      idCheck(userid, (response) => {
        if (response.data === "success") {
          commit("ID_CHECK_OK", true);
        } else {
          commit("ID_CHECK_NOT_OK", false);
        }
      });
    },
    deleteOneUser(state, userid) {
      deleteUser(userid, (response) => {
        if (response.data === "success") {
          alert("삭제되었습니다!");
        }
      });
    },
    updateOneUser({ commit }, user) {
      update(user, (response) => {
        if (response.data === "success") {
          commit("SET_USER_INFO", response.data.userInfo);
        } else {
          console.log("유저 업데이트 실패!!");
        }
      });
    },
  },
};

export default memberStore;
