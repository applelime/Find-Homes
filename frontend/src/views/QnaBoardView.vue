<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle">
      <h1 style="color:white;">Q&amp;A 게시판</h1>
    </parallax>
    <div class="main main-raised">
      <div class="section profile-content" style="padding-top: 0px;">
        <div class="container">
          <div class="text-center" style="min-height: 600px;">
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QnaBoardView",
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
  },
};
</script>

<style scoped></style>
