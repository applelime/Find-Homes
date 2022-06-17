<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">Login</h4>
              <p slot="description" class="description">로그인</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>ID</label>
                <md-input
                  v-model="user.userid"
                  required
                  @keyup.enter="confirm()"
                ></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password</label>
                <md-input
                  v-model="user.userpw"
                  type="password"
                  @keyup.enter="confirm()"
                ></md-input>
              </md-field>
              <md-button
                slot="footer"
                class="md-simple md-success md-lg"
                @click="confirm()"
              >
                로그인
              </md-button>
              <md-button
                slot="footer"
                class="md-simple md-success md-lg"
                @click="movePage()"
              >
                회원 가입하기
              </md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginCard } from "@/components";

import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
  components: {
    LoginCard,
  },
  bodyClass: "login-page",
  data() {
    return {
      user: {
        userid: null,
        userpw: null,
      },
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/seoul.jpg"),
    },
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),

    headerStyle() {
      return {
        // backgroundImage: `url(${this.header})`,
        backgroundColor: "white",
      };
    },
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "home" });
      }
    },
    movePage() {
      console.log("이동발생");
      this.$router.push({ name: "signUp" });
    },
  },
};
</script>

<style lang="css" scoped>
.page-header::before {
  background: rgba(0, 0, 0, 0) !important;
}

.card-title {
  font-weight: 0;
  font-family: Arial, Helvetica, sans-serif;
  margin-bottom: 0;
  margin-top: 10px;
}
</style>
