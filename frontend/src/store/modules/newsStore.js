import { getNewList } from "@/api/news.js";
const newsStore = {
  namespaced: true,
  state: { todayNewList: null },
  getters: {},
  mutations: {
    SET_NEWS_LIST: (state, list) => {
      state.todayNewList = list;
    },
  },
  actions: {
    async getTodayNewList({ commit }) {
      await getNewList((response) => {
        commit("SET_NEWS_LIST", response.data);
      });
    },
  },
};
export default newsStore;
