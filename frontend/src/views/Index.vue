<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <h1>Find Homes</h1>
              <h3>당신의 보금자리 찾기</h3>
            </div>
            <div style="margin-top: 40px;">
              <b-input-group class="mt-3">
                <template #append>
                  <b-button @click="moveMap()">
                    <b-icon icon="search"></b-icon>
                  </b-button>
                </template>
                <b-form-input
                  id="word"
                  v-model="searchWord"
                  placeholder="원하시는 지역명을 입력해주세요"
                  @keyup.enter="moveMap()"
                  @input="searchName()"
                ></b-form-input>
              </b-input-group>
              <b-list-group
                style="width: 100%; padding-right: 30px; position: absolute"
              >
                <b-list-group-item
                  v-for="(item, index) in searchResultList"
                  :key="index"
                  @click="selectSearchList(index)"
                  >{{ item.address_name }}
                </b-list-group-item>
              </b-list-group>
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-tabs section-white">
        <div class="container">
          <tabs :newsList="this.todayNewList"></tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import Tabs from "@/components/TabsSection";
const newsStore = "newsStore";
const houseStore = "houseStore";

export default {
  components: {
    Tabs,
  },
  name: "index",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/seoul.jpg"),
    },
  },
  data() {
    return {
      searchWord: "",
    };
  },
  created() {
    this.initResultList();
    this.getTodayNewList();
  },
  methods: {
    moveMap() {
      if (!this.searchResultList.length) return;
      this.$router.push({
        name: "map",
        params: {
          lng: this.searchResultList[0].x,
          lat: this.searchResultList[0].y,
        },
      });
    },
    // 선택하면 해당 좌표로 이동
    selectSearchList(index) {
      this.$router.push({
        name: "map",
        params: {
          lng: this.searchResultList[index].x,
          lat: this.searchResultList[index].y,
        },
      });
    },
    searchName() {
      this.getSearchWordList(this.searchWord);
    },
    ...mapActions(newsStore, ["getTodayNewList"]),
    ...mapActions(houseStore, ["initResultList", "getSearchWordList"]),
  },
  computed: {
    ...mapState(newsStore, ["todayNewList"]),
    ...mapState(houseStore, ["searchResultList"]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.image})`,
      };
    },
  },
};
</script>
<style lang="scss">
.section-download {
  .md-button + .md-button {
    margin-left: 5px;
  }
}

@media all and (min-width: 991px) {
  .btn-container {
    display: flex;
  }
}

.section-tabs {
  background: white !important;
}
</style>
