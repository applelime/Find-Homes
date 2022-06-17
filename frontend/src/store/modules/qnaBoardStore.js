import {
  listQnaBoard,
  writeQnaBoard,
  getQnaBoard,
  modifyQnaBoard,
  deleteQnaBoard,
  addQnaBoardCount,
} from "@/api/qnaBoard.js";
import router from "@/router";

const qnaBoardStore = {
  namespaced: true,
  state: {
    qnaBoardList: null,
    qnaBoard: null,
  },
  getters: {
    checkQnaBoardList: function(state) {
      return state.qnaBoardList;
    },
    checkOneQnaBoard: function(state) {
      return state.qnaBoard;
    },
  },
  mutations: {
    SET_QNA_BOARDLIST: (state, list) => {
      state.qnaBoardList = list;
    },
    SET_ONE_QNA_BOARD: (state, board) => {
      state.qnaBoard = board;
    },
  },
  actions: {
    async getQnaBoardList({ commit }) {
      await listQnaBoard((response) => {
        commit("SET_QNA_BOARDLIST", response.data);
      });
    },
    async getOneQnaBoard({ commit }, num) {
      await getQnaBoard(num, (response) => {
        commit("SET_ONE_QNA_BOARD", response.data);
        router.push({
          name: "qnaDetail",
          params: { num },
        });
      });
    },
    async deleteQnaBoard(context, num) {
      await deleteQnaBoard(num, (response) => {
        if (response.status == 200) {
          alert("삭제가 완료되었습니다.");
          router.push({
            name: "qnaList",
          });
        }
      });
    },
    async createQnaBoard(context, board) {
      await writeQnaBoard(board, (response) => {
        if (response.status == 200) {
          router.push({
            name: "qnaList",
          });
        }
      });
    },
    async updateQnaBoard(context, board) {
      await modifyQnaBoard(board, (response) => {
        if (response.status == 200) {
          alert("수정이 완료되었습니다.");
          router.push({
            name: "qnaList",
          });
        }
      });
    },
    async updateQnaBoardCount(state, num) {
      await addQnaBoardCount(num);
    },
  },
};

export default qnaBoardStore;
