<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">회원 가입</h4>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>fingerprint</md-icon>
                <label>Id</label>
                <md-input v-model="user.userid" required></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="user.email" type="email"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input v-model="user.userpw" type="password"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>badge</md-icon>
                <label>nickname...</label>
                <md-input v-model="user.nickname"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>phone_iphone</md-icon>
                <label>Phone...</label>
                <md-input v-model="user.phone"></md-input>
              </md-field>
              <md-button
                slot="footer"
                class="md-simple md-success md-lg"
                @click="confirm"
              >
                가입하기
              </md-button>
              <md-button
                slot="footer"
                class="md-simple md-success md-lg"
                @click="movePage"
              >
                로그인 가기
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
        email: null,
        userpw: null,
        phone: null,
        nickname: null,
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
    ...mapState(memberStore, ["isIdCheck"]),
    headerStyle() {
      return {
        // backgroundImage: `url(${this.header})`,
        backgroundColor: "white",
      };
    },
  },
  methods: {
    ...mapActions(memberStore, ["registerUser", "idConfirm"]), //수정해야함.. 가입하는걸로
    async confirm() {
      console.log(this.user.userid);
      await this.idConfirm(this.user.userid);
      if (this.isIdCheck) {
        this.registerUser(this.user);

        alert("회원 가입 성공! ");
        this.$router.push({ name: "home" });
      } else {
        alert("id가 유효하지 않습니다!");
      }
    },
    movePage() {
      console.log("이동발생");
      this.$router.push({ name: "signIn" });
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
