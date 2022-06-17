<template>
  <div id="area-search">
    <b-input-group>
      <template #append>
        <b-button @click="moveMap()">
          <b-icon icon="search"></b-icon>
        </b-button>
      </template>
      <b-form-input
        style="border: 0; padding-left: 10px;"
        id="word"
        v-model="searchWord"
        placeholder="원하시는 지역명을 입력해주세요"
        @keyup.enter="moveMap()"
        @input="searchName($event)"
      ></b-form-input>
    </b-input-group>
    <b-list-group>
      <b-list-group-item
        style="padding: 10px;"
        v-for="(item, index) in searchResultList"
        :key="index"
        @click="selectSearchList(index)"
        >{{ item.address_name }}
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const houseStore = "houseStore";
export default {
  components: {},
  data() {
    return {
      searchWord: "", // 검색창 검색어
    };
  },
  created() {
    this.initResultList();
  },
  methods: {
    ...mapActions(houseStore, ["initResultList", "getSearchWordList"]),

    searchName(event) {
      if (event.key == "Enter") return;
      this.getSearchWordList(this.searchWord);
    },

    // 검색결과 리스트 선택하면 해당 좌표로 이동
    selectSearchList(index) {
      this.$emit(
        "moveMapByPos",
        this.searchResultList[index].y,
        this.searchResultList[index].x
      );
      this.searchWord = "";
      this.initResultList();
    },

    // 엔터나 버튼 클릭하면 1번째 결과(0번) 위치로 이동
    moveMap() {
      if (!this.searchResultList.length) return;
      this.$emit(
        "moveMapByPos",
        this.searchResultList[0].y,
        this.searchResultList[0].x
      );
      this.searchWord = "";
      this.initResultList();
    },
  },
  computed: {
    ...mapState(houseStore, ["searchResultList"]),
  },
};
</script>

<style lang="scss" scoped>
#area-search {
  position: absolute;
  top: 50px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 3;
  width: 600px;
}
</style>
