<template>
  <md-card class="md-layout-item" style="min-height: 527px;">
    <br />
    <md-card-content>
      <md-field style="max-width:none">
        <label>제목</label>
        <md-input v-model="title"></md-input>
      </md-field>
      <md-field style="max-width:none; margin:0px">
        <label>내용</label>
        <md-textarea
          v-model="content"
          style="height:300px; max-height:300px; padding: 0px;"
        ></md-textarea>
      </md-field>
    </md-card-content>
    <b-button
      variant="success"
      @click="registerNotice"
      style="position: absolute; bottom: 30px; width: 100px;"
      >등록</b-button
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
      title: "",
      content: "",
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
    async registerNotice() {
      if (this.title === "" || this.content === "") {
        alert("내용을 다 써주세요!");
      } else {
        let notice = {
          userId: this.userInfo.userid,
          title: this.title,
          content: this.content,
        };
        await this.createNotice(notice);
        this.title = "";
        this.content = "";
      }
    },
  },
  async created() {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  mounted() {},
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
