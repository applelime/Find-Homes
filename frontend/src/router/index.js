import Vue from "vue";
import VueRouter from "vue-router";
import Index from "@/views/Index.vue";
import MemberView from "@/views/MemberView.vue";
import MapView from "@/views/MapView.vue";

import NoticeView from "@/views/NoticeView.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeDetail from "@/components/notice/NoticeDetail.vue";
import NoticeCreate from "@/components/notice/NoticeCreate.vue";

import QnaBoardView from "@/views/QnaBoardView.vue";
import QnaBoardList from "@/components/qna/QnaBoardList.vue";
import QnaBoardDetail from "@/components/qna/QnaBoardDetail.vue";
import QnaBoardCreate from "@/components/qna/QnaBoardCreate.vue";

import Login from "@/components/user/Login.vue";
import SignUp from "@/components/user/SignUp.vue";
import MyPage from "@/components/user/MyPage.vue";
import MainNavbar from "@/layout/MainNavbar.vue";
import MainFooter from "@/layout/MainFooter.vue";

import store from "@/store";

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }

  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    components: { default: Index, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/map",
    name: "map",
    components: { default: MapView, header: MainNavbar },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/notice",
    name: "notice",
    redirect: "/notice/list",
    components: { default: NoticeView, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
    children: [
      {
        path: "list",
        name: "noticeList",
        components: {
          default: NoticeList,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
      {
        path: "write",
        name: "noticeRegister",
        components: {
          default: NoticeCreate,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
      {
        path: "detail/:num",
        name: "noticeDetail",
        components: {
          default: NoticeDetail,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
    ],
  },
  {
    path: "/qna",
    name: "qna",
    redirect: "/qna/list",
    components: {
      default: QnaBoardView,
      header: MainNavbar,
      footer: MainFooter,
    },
    children: [
      {
        path: "list",
        name: "qnaList",
        components: {
          default: QnaBoardList,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
      {
        path: "create",
        name: "qnaCreate",
        components: {
          default: QnaBoardCreate,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
      {
        path: "detail/:num",
        name: "qnaDetail",
        components: {
          default: QnaBoardDetail,
          header: MainNavbar,
          footer: MainFooter,
        },
        props: {
          header: { colorOnScroll: 0 },
          footer: { backgroundColor: "black" },
        },
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    redirect: "/user/signin",
    components: { default: MemberView, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
    },

    children: [
      {
        path: "signin",
        name: "signIn",
        components: { default: Login },
        props: {
          header: { colorOnScroll: 400 },
        },
      },

      {
        path: "signup",
        name: "signUp",
        components: { default: SignUp, header: MainNavbar, footer: MainFooter },
        props: {
          header: { colorOnScroll: 400 },
        },
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        components: { default: MyPage, header: MainNavbar, footer: MainFooter },
        props: {
          header: { colorOnScroll: 400 },
        },
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
