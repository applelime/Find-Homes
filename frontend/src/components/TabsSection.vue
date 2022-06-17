<template>
  <div class="wrapper">
    <div id="nav-tabs">
      <div class="md-layout">
        <div class="md-layout-item md-size-50 md-small-size-100">
          <nav-tabs-card no-label tabs-plain>
            <template slot="content">
              <md-tabs class="md-primary" md-alignment="left">
                <md-tab
                  id="tab-home"
                  md-label="News"
                  md-icon="face"
                  style="text-align: initial;"
                >
                  <div>
                    <carousel
                      :per-page="1"
                      loop
                      :speed="700"
                      autoplay
                      :autoplay-timeout="5000"
                      :mouse-drag="false"
                      navigationEnabled
                      navigationNextLabel="<i class='material-icons' style='color: skyblue !important;
    opacity: 1.0;'>keyboard_arrow_right</i>"
                      navigationPrevLabel="<i class='material-icons' style='color: skyblue !important;
    opacity: 1.0;'>keyboard_arrow_left</i>"
                    >
                      <slide v-for="(page, index) in newsBoard" :key="index">
                        <md-card
                          md-with-hover
                          class="news-card"
                          v-for="(news, index) in page"
                          :key="index"
                          @click.native="openNews(news.link)"
                        >
                          <md-ripple>
                            <div class="md-title news-title">
                              {{ news.title }}
                            </div>

                            <md-card-content class="news-content">
                              {{ news.content }}
                            </md-card-content>
                          </md-ripple>
                        </md-card>
                        <div id="navigation-area"></div>
                      </slide>
                    </carousel>
                  </div>
                </md-tab>
              </md-tabs>
            </template>
          </nav-tabs-card>
        </div>
        <div class="md-layout-item md-size-50 md-small-size-100">
          <nav-tabs-card no-label tabs-plain>
            <template slot="content">
              <md-tabs class="md-danger" md-alignment="left">
                <md-tab id="tab-home" md-label="공지사항">
                  <md-table v-model="noticeList" @md-selected="onSelect">
                    <md-table-row
                      slot="md-table-row"
                      slot-scope="{ item }"
                      md-selectable="single"
                    >
                      <md-table-cell md-label="번호" md-numeric>{{
                        item.num
                      }}</md-table-cell>
                      <md-table-cell md-label="작성자" md-sort-by="name">{{
                        item.userId
                      }}</md-table-cell>
                      <md-table-cell md-label="제목" md-sort-by="email">{{
                        item.title
                      }}</md-table-cell>
                      <md-table-cell md-label="작성일">{{
                        item.writeDate
                      }}</md-table-cell>
                    </md-table-row>
                  </md-table>
                </md-tab>

                <md-tab id="tab-pages" md-label="QnA 게시판">
                  <md-table v-model="qnaBoardList" @md-selected="onSelectBoard">
                    <md-table-row
                      slot="md-table-row"
                      slot-scope="{ item }"
                      md-selectable="single"
                    >
                      <md-table-cell md-label="번호" md-numeric>{{
                        item.num
                      }}</md-table-cell>
                      <md-table-cell md-label="작성자" md-sort-by="name">{{
                        item.userId
                      }}</md-table-cell>
                      <md-table-cell md-label="제목" md-sort-by="email">{{
                        item.title
                      }}</md-table-cell>
                      <md-table-cell md-label="작성일">{{
                        item.writeDate
                      }}</md-table-cell>
                    </md-table-row>
                  </md-table>
                </md-tab>
              </md-tabs>
            </template>
          </nav-tabs-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { NavTabsCard } from "@/components";
import { mapState, mapActions } from "vuex";

const noticeStore = "noticeStore";
const qnaBoardStore = "qnaBoardStore";
export default {
  components: {
    NavTabsCard,
  },
  props: {
    newsList: Array,
  },
  data() {
    return {
      newsBoard: [],
    };
  },
  created() {
    this.getNoticeList();
    this.getQnaBoardList();
  },
  computed: {
    ...mapState(noticeStore, ["noticeList"]),
    ...mapState(qnaBoardStore, ["qnaBoardList"]),
  },
  methods: {
    ...mapActions(noticeStore, ["getNoticeList", "getOneNotice"]),
    ...mapActions(qnaBoardStore, ["getQnaBoardList", "getOneQnaBoard"]),
    openNews(url) {
      window.open(url);
    },
    onSelect(item) {
      this.getOneNotice(item.num);
    },
    onSelectBoard(item) {
      this.getOneQnaBoard(item.num);
    },
  },
  watch: {
    newsList(newList) {
      let start = 0;
      let end = 4;
      let max = newList.length < end ? newList.length : end;

      this.newsBoard = [];
      while (start < max) {
        let page = [];
        for (let i = start; i < max; i++) {
          page.push(newList[i]);
        }
        this.newsBoard.push(page);

        start = start + 4;
        end = end + 4;
        max = newList.length < end ? newList.length : end;
      }
      console.log(this.newsBoard);
    },
  },
};
</script>

<style lang="css" scoped>
.news-title {
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  font-size: 18px !important;
  font-weight: bold !important;
  padding: 10px 10px 0px 10px;
}

.news-content {
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
  padding: 10px 10px 0px 10px !important;
}

.news-card {
  width: 45%;
  height: 180px;
  margin: 10px;
  display: inline-block;
  text-align: center;
}

#navigation-area {
  height: 50px;
}
</style>
