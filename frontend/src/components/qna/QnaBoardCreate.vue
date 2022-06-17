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
      @click="createQnaBoard({ title, content, userId: userInfo.userid })"
      style="position: absolute; bottom: 30px; width: 100px;"
      >등록</b-button
    >
  </md-card>
</template>

<script>
import { mapActions, mapState } from "vuex";

const qnaBoardStore = "qnaBoardStore";
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
  methods: {
    ...mapActions(qnaBoardStore, ["createQnaBoard"]),
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
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
