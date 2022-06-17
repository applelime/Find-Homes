<template>
  <md-toolbar
    id="toolbar"
    md-elevation="0"
    class="md-transparent md-absolute"
    :class="extraNavClasses"
    :color-on-scroll="colorOnScroll"
  >
    <div class="md-toolbar-row md-collapse-lateral">
      <div class="md-toolbar-section-start">
        <router-link class="md-title md-logo" to="/">Find Homes</router-link>
        <router-link class="md-title" to="/map">거래가 검색</router-link>
        <router-link class="md-title" to="/notice">공지사항</router-link>
        <router-link class="md-title" to="/qna">Q&amp;A게시판</router-link>
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: toggledClass }"
          @click="toggleNavbarMobile()"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <div class="md-collapse-wrapper">
            <mobile-menu nav-mobile-section-start="false">
              <!-- Here you can add your items from the section-start of your toolbar -->
            </mobile-menu>

            <md-list>
              <div class="ml-auto" v-if="userInfo">
                <div class="md-toolbar-row">
                  <div class="md-toolbar-section-start">
                    <div class="md-title">
                      {{ userInfo.nickname }}({{ userInfo.userid }})님
                      환영합니다.
                    </div>

                    <router-link
                      v-if="userInfo.usertype == 'admin'"
                      :to="{ name: 'mypage' }"
                      class="link align-self-center md-title"
                      >회원 관리
                    </router-link>

                    <router-link
                      v-else
                      :to="{ name: 'mypage' }"
                      class="link align-self-center md-title"
                      >내정보보기
                    </router-link>
                    <a
                      href="#"
                      class="link align-self-center md-title"
                      @click.prevent="onClickLogout"
                    >
                      로그아웃
                    </a>
                  </div>
                </div>
              </div>
              <div class="ml-auto" v-else>
                <div class="md-toolbar-row">
                  <div class="md-toolbar-section-start">
                    <router-link
                      :to="{ name: 'signUp' }"
                      class="link align-self-center md-title"
                      >회원가입
                    </router-link>
                    <router-link
                      :to="{ name: 'signIn' }"
                      class="link align-self-center md-title"
                      >로그인
                    </router-link>
                  </div>
                </div>
              </div>
            </md-list>
          </div>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import MobileMenu from "@/layout/MobileMenu";
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  components: {
    MobileMenu,
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info",
        ].includes(value);
      },
    },
    colorOnScroll: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      extraNavClasses: "",
      toggledClass: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // console.log("memberStore : ", ms);
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = "";
          navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      // resizeThrottler(this.handleScroll);
    },
    scrollToElement() {
      let element_id = document.getElementById("downloadSection");
      if (element_id) {
        element_id.scrollIntoView({ block: "end", behavior: "smooth" });
      }
    },
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  },
};
</script>
<style scoped="scss">
.dropdown-toggle::after {
  content: none;
}
#toolbar {
  /* background-color: black !important; */
  background: linear-gradient(to right, #616161, #9bc5c3) !important;
  padding: 10px 15px !important;
}

#toolbar .md-title {
  color: white !important;
  font-size: 16px;
  margin-right: 30px;
  vertical-align: middle;
}

#toolbar .md-logo {
  font-size: 18px;
  font-weight: bold;
}
</style>
