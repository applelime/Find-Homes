import {
  listNotice,
  writeNotice,
  getNotice,
  modifyNotice,
  deleteNotice,
  addCount,
} from "@/api/notice.js";
import router from "@/router";

const noticeStore = {
  namespaced: true,
  state: {
    noticeList: null,
    notice: null,
  },
  getters: {
    checkNoticeList: function(state) {
      return state.noticeList;
    },
    checkOneNotice: function(state) {
      return state.notice;
    },
  },
  mutations: {
    SET_NOTICE_LIST: (state, list) => {
      state.noticeList = list;
    },
    SET_ONE_NOTICE: (state, notice) => {
      state.notice = notice;
    },
  },
  actions: {
    async getNoticeList({ commit }) {
      await listNotice((response) => {
        commit("SET_NOTICE_LIST", response.data);
      });
    },
    getOneNotice({ commit }, num) {
      getNotice(num, (response) => {
        commit("SET_ONE_NOTICE", response.data);
        router.push({
          name: "noticeDetail",
          params: { num },
        });
      });
    },
    async removeOneNotice(context, num) {
      await deleteNotice(num, (response) => {
        if (response.data === "success") {
          alert("삭제가 완료되었습니다.");
          router.push({
            name: "noticeList",
          });
        }
      });
    },
    async createNotice(context, board) {
      await writeNotice(board, (response) => {
        if (response.data === "success") {
          router.push({
            name: "noticeList",
          });
        }
      });
    },
    async updateNotice(context, board) {
      await modifyNotice(board, (response) => {
        if (response.data === "success") {
          alert("수정이 완료되었습니다.");
          router.push({
            name: "noticeList",
          });
        }
      });
    },
    async updateNoticeCount(state, num) {
      await addCount(num);
    },
  },
};

export default noticeStore;
