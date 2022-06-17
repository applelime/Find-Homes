<template>
  <md-table
    v-model="this.noticeList"
    md-card
    md-fixed-header
    @md-selected="onSelect"
  >
    <md-table-toolbar>
      <div class="md-toolbar-section-start">
        <h3 style="color:black;">공지사항</h3>
      </div>
      <div class="ml-auto" v-if="userInfo">
        <b-button
          v-if="this.userInfo.usertype === 'admin'"
          variant="primary"
          @click="onCreate"
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

const noticeStore = "noticeStore";
const memberStore = "memberStore";

export default {
  components: {},
  bodyClass: "profile-page",
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
    onCreate() {
      this.$router.push({
        name: "noticeRegister",
      });
    },
    onSelect(item) {
      this.getOneNotice(item.num);
    },
  },
  created() {
    this.getNoticeList();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapState(noticeStore, ["noticeList", "notice"]),
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
