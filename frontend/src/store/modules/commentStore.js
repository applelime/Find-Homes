import {
  listComment,
  writeComment,
  modifyComment,
  deleteComment,
} from "@/api/comment.js";
const commentStore = {
  namespaced: true,
  state: {
    commentList: null,
  },
  getters: {
    checkCommentList: function(state) {
      return state.commentList;
    },
  },
  mutations: {
    SET_COMMENT_LIST: (state, list) => {
      state.commentList = list;
    },
  },
  actions: {
    async getCommentList({ commit }, boardNum) {
      await listComment(boardNum, (response) => {
        commit("SET_COMMENT_LIST", response.data);
      });
    },
    async remoceComment({ dispatch }, num) {
      await deleteComment(num, (response) => {
        if (response.data === "success") dispatch("getCommentList");
      });
    },
    async createComment({ dispath }, comment) {
      await modifyComment(comment, (response) => {
        if (response.data === "success") dispath("getCommentList");
      });
    },
    async updateComment({ dispath }, comment) {
      await writeComment(comment, (response) => {
        if (response.data === "success") dispath("getCommentList");
      });
    },
  },
};
export default commentStore;
