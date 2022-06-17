<template>
  <md-card class="md-layout-item" style="min-height: 527px;">
    <br />
    <md-card-content>
      <div class="md-layout md-gutter">
        <div class="md-layout-item md-small-size-100" v-if="notice">
          <md-field>
            <label>공지사항 번호</label>
            <md-input v-model="notice.num" disabled></md-input>
          </md-field>
        </div>

        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>작성자</label>
            <md-input v-model="notice.userId" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>조회수</label>
            <md-input v-model="notice.count" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>작성일</label>
            <md-input v-model="notice.writeDate" disabled></md-input>
          </md-field>
        </div>
        <div class="md-layout-item md-small-size-100">
          <md-field>
            <label>수정일</label>
            <md-input v-model="notice.lastModifyDate" disabled></md-input>
          </md-field>
        </div>
      </div>
      <md-field style="max-width:none">
        <label>제목</label>
        <md-input
          v-model="notice.title"
          :disabled="updateCheckFlag === false"
        ></md-input>
      </md-field>
      <md-field style="max-width:none; margin:0px">
        <label>내용</label>
        <md-textarea
          v-model="notice.content"
          :disabled="updateCheckFlag === false"
          style="height:250px; max-height:250px; padding: 0px;"
        ></md-textarea>
      </md-field>
    </md-card-content>

    <b-button
      variant="warning mr-3"
      v-if="!updateCheckFlag && userInfo && userInfo.usertype === 'admin'"
      @click="amendNotice"
      style="position: absolute; bottom: 30px; left: 40%; width: 100px;"
      >수정</b-button
    >
    <b-button
      variant="success mr-3"
      v-if="updateCheckFlag && userInfo && userInfo.usertype === 'admin'"
      @click="amendNotice"
      style="position: absolute; bottom: 30px; left: 40%; width: 100px;"
      >수정완료</b-button
    >
    <b-button
      variant="danger"
      v-if="userInfo && userInfo.usertype === 'admin'"
      @click="deleteNoticeByNum"
      style="position: absolute; bottom: 30px; right: 40%; width:100px;"
      >삭제</b-button
    >
  </md-card>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const noticeStore = "noticeStore";
const memberStore = "memberStore";
export default {
  components: {},
  bodyClass: "profile-page",
  data() {
    return {
      updateCheckFlag: false,
      board: {
        userId: "",
        num: "",
        count: "",
        writeDate: "",
        lastModifyDate: "",
      },
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
    img: {
      type: String,
      default: require("@/assets/img/faces/christian.jpg"),
    },
  },
  methods: {
    ...mapActions(noticeStore, [
      "getNoticeList",
      "getOneNotice",
      "removeOneNotice",
      "createNotice",
      "updateNotice",
      "updateNoticeCount",
    ]),
    ...mapMutations(noticeStore, ["SET_NOTICE_LIST", "SET_ONE_NOTICE"]),
    async amendNotice() {
      if (this.updateCheckFlag) {
        // console.log(this.notice);
        await this.updateNotice(this.notice);
      }
      this.updateCheckFlag = !this.updateCheckFlag;
    },
    deleteNoticeByNum() {
      this.removeOneNotice(this.notice.num);
    },
  },
  async created() {
    let num = this.$route.params.num;

    await this.getOneNotice(num);
    await this.updateNoticeCount(num);
    await this.getOneNotice(num);
    this.board = this.notice;
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(noticeStore, ["notice"]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  mounted() {
    // this.board = this.notice;
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
