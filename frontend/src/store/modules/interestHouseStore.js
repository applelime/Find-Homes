import {
  findInterestHouse,
  addInterest,
  deleteInterest,
  findInterestOffice,
} from "@/api/interestHouse.js";

const interestStore = {
  namespaced: true,
  state: {
    interestHouseList: null,
    interestOfficetelList: null,
    userid: null,
  },
  getters: {
    getInterestHouseList: function(state) {
      return state.interestHouseList;
    },
    getInterestOfficetelList: function(state) {
      return state.interestOfficetelList;
    },
    getUserId: function(state) {
      return state.userid;
    },
  },
  mutations: {
    SET_APT_LIST: (state, list) => {
      state.interestHouseList = list;
    },

    SET_OFFICE_LIST: (state, list) => {
      state.interestOfficetelList = list;
    },
    SET_USERID: (state, id) => {
      state.userid = id;
    },
  },
  actions: {
    async interestAptList({ commit }, data) {
      if (data.type === "apt") {
        await findInterestHouse(data.userid, (response) => {
          commit("SET_APT_LIST", response.data);
        });
      } else {
        await findInterestOffice(data.userid, (response) => {
          commit("SET_OFFICE_LIST", response.data);
        });
      }
    },
    async addInterestHouse({ dispatch }, data) {
      await addInterest(data, (response) => {
        if (response.data === "success") {
          let user = { userid: data.userid, type: "apt" };
          dispatch("interestAptList", user);

          let user2 = { userid: data.userid, type: "officetel" };
          dispatch("interestAptList", user2);
        }
      });
    },
    async deleteInterestHouse({ dispatch, state }, house) {
      await deleteInterest(house.num, (response) => {
        if (response.data === "success") {
          if (house.house_type === "apt") {
            let user = { userid: state.userid, type: "apt" };
            dispatch("interestAptList", user);
          } else {
            let user2 = { userid: state.userid, type: "officetel" };
            dispatch("interestAptList", user2);
          }
        }
      });
    },
    setUserId({ commit }, userid) {
      commit("SET_USERID", userid);
    },
  },
};

export default interestStore;
