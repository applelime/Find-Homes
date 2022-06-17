<template>
  <md-card class="md-layout-item" style="min-height: 527px;">
    <br />
    <md-card-content>
      <div class="md-layout md-gutter">
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>게시물 번호</label>
            <md-input v-model="qnaBoard.num" disabled></md-input>
          </md-field>
        </div>

        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>작성자</label>
            <md-input v-model="qnaBoard.userId" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>조회수</label>
            <md-input v-model="qnaBoard.count" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>작성일</label>
            <md-input v-model="qnaBoard.writeDate" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>수정일</label>
            <md-input v-model="qnaBoard.lastModifyDate" disabled></md-input>
          </md-field>
        </div>
      </div>
      <md-field style="max-width:none">
        <label>제목</label>
        <md-input v-model="qnaBoard.title" :disabled="!modifyMode"></md-input>
      </md-field>
      <md-field style="max-width:none; margin:0px">
        <label>내용</label>
        <md-textarea
          v-model="qnaBoard.content"
          :disabled="!modifyMode"
          style="height:250px; max-height:250px; padding: 0px;"
        ></md-textarea>
      </md-field>
    </md-card-content>
    <b-button
      variant="warning mr-3"
      v-if="!modifyMode && userInfo && userInfo.userid == qnaBoard.userId"
      @click="modifyMode = true"
      style="position: absolute; bottom: 30px; left: 40%; width: 100px;"
      >수정</b-button
    >
    <b-button
      variant="success mr-3"
      v-if="modifyMode && userInfo && userInfo.userid == qnaBoard.userId"
      @click="updateQnaBoard(qnaBoard)"
      style="position: absolute; bottom: 30px; left: 40%; width: 100px;"
      >수정완료</b-button
    >
    <b-button
      variant="danger"
      v-if="userInfo && userInfo.userid == qnaBoard.userId"
      @click="deleteQnaBoard(qnaBoard.num)"
      style="position: absolute; bottom: 30px; right: 40%; width:100px;"
      >삭제</b-button
    >
  </md-card>
</template>

<script>
import { mapState, mapActions } from "vuex";

const qnaBoardStore = "qnaBoardStore";
const memberStore = "memberStore";

export default {
  components: {},
  bodyClass: "profile-page",
  data() {
    return {
      modifyMode: false,
    };
  },
  methods: {
    ...mapActions(qnaBoardStore, [
      "updateQnaBoardCount",
      "updateQnaBoard",
      "deleteQnaBoard",
    ]),
  },
  created() {
    // console.log(this.$route.params.num);
    // let num = this.$route.params.num;
    this.updateQnaBoardCount(this.qnaBoard.num);
    // await this.getOneNotice(num);
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(qnaBoardStore, ["qnaBoard"]),
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
