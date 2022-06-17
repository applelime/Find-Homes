import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import memberStore from "@/store/modules/memberStore.js";
import houseStore from "@/store/modules/houseStore.js";
import interestStore from "@/store/modules/interestHouseStore.js";
import newsStore from "@/store/modules/newsStore.js";
import qnaBoardStore from "@/store/modules/qnaBoardStore.js";
import noticeStore from "@/store/modules/noticeStore.js";
import commentStore from "@/store/modules/commentStore.js";

const store = new Vuex.Store({
  modules: {
    memberStore,
    houseStore,
    interestStore,
    newsStore,
    qnaBoardStore,
    noticeStore,
    commentStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
