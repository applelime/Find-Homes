<template>
  <md-table
    v-model="this.qnaBoardList"
    md-card
    md-fixed-header
    @md-selected="onSelect"
  >
    <md-table-toolbar>
      <div class="md-toolbar-section-start">
        <h3 style="color:black;">Q&amp;A 게시판</h3>
      </div>
      <div class="ml-auto" v-if="userInfo">
        <b-button
          variant="primary"
          @click="$router.push({ name: 'qnaCreate' })"
          style="width: 100px;"
          >글쓰기</b-button
        >
      </div>
    </md-table-toolbar>

    <md-table-row
      slot="md-table-row"
      slot-scope="{ item }"
      md-selectable="single"
    >
      <md-table-cell md-label="" style="text-align: center;"> </md-table-cell>
      <md-table-cell md-label="번호" md-numeric style="text-align: center;">{{
        item.num
      }}</md-table-cell>
      <md-table-cell md-label="작성자" style="text-align: center;">{{
        item.userId
      }}</md-table-cell>
      <md-table-cell md-label="제목" style="text-align: center;">{{
        item.title
      }}</md-table-cell>
      <md-table-cell md-label="조회수" md-numeric style="text-align: center;">{{
        item.count
      }}</md-table-cell>
      <md-table-cell md-label="작성일" style="text-align: center;">{{
        item.writeDate
      }}</md-table-cell>
      <md-table-cell md-label="" style="text-align: center;"> </md-table-cell>
    </md-table-row>
  </md-table>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const qnaBoardStore = "qnaBoardStore";
const memberStore = "memberStore";

export default {
  components: {},
  bodyClass: "profile-page",
  methods: {
    ...mapActions(qnaBoardStore, [
      "getQnaBoardList",
      "getOneQnaBoard",
      "deleteQnaBoard",
      "createQnaBoard",
      "updateQnaBoard",
    ]),
    ...mapMutations(qnaBoardStore, ["SET_QNA_BOARDLIST", "SET_ONE_QNA_BOARD"]),
    onSelect(item) {
      this.getOneQnaBoard(item.num);
    },
  },
  created() {
    this.getQnaBoardList();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapState(qnaBoardStore, ["qnaBoardList", "qnaBoard"]),
  },
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}
.md-field {
  max-width: 300px;
}
</style>

<style>
.md-table-head-container {
  text-align: center !important;
}

.md-table-head-label {
  padding: 0px !important;
}
</style>
