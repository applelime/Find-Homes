<template>
  <div>
    <!-- 필터 버튼 -->
    <button id="filterButton" @click="filterShow = !filterShow">
      <b-icon icon="filter"></b-icon>
      필터
    </button>
    <!-- 필터창 -->
    <div id="filter" v-if="filterShow">
      <md-list>
        <md-list-item>
          <div>
            <b-icon
              icon="x"
              style="width:25px; height:25px; vertical-align:middle"
              @click="filterShow = false"
            ></b-icon>
            <span
              style="font-size: 15px; font-weight: bold; vertical-align:middle"
              >필터</span
            >
          </div>
          <div>
            <p @click="resetFilter()">초기화</p>
          </div>
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <div class="filter-title">거래유형</div>
        </md-list-item>
        <md-list-item>
          <b-button
            variant="outline-secondary"
            @click="selectFilterHouseType('all')"
            :pressed="filterHouseType == 'all'"
            >전체</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterHouseType('apt')"
            :pressed="filterHouseType == 'apt'"
            >아파트</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterHouseType('officetel')"
            :pressed="filterHouseType == 'officetel'"
            >오피스텔</b-button
          >
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <div class="filter-title">건축년도</div>
        </md-list-item>
        <md-list-item>
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(0)"
            :pressed="filterBuildYear == 0"
            >전체</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(5)"
            :pressed="filterBuildYear == 5"
            >5년 이내</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(10)"
            :pressed="filterBuildYear == 10"
            >10년 이내</b-button
          >
        </md-list-item>
        <md-list-item>
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(15)"
            :pressed="filterBuildYear == 15"
            >15년 이내</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(20)"
            :pressed="filterBuildYear == 20"
            >20년 이내</b-button
          >
          <b-button
            variant="outline-secondary"
            @click="selectFilterBuildYear(25)"
            :pressed="filterBuildYear == 25"
            >25년 이내</b-button
          >
        </md-list-item>
      </md-list>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: ["filterHouseType", "filterBuildYear"],
  data() {
    return {
      filterShow: false,
    };
  },
  created() {
    this.filterShow = false;
  },
  methods: {
    resetFilter() {
      this.$emit("resetFilter");
    },

    selectFilterHouseType(type) {
      this.$emit("selectFilterHouseType", type);
    },

    selectFilterBuildYear(year) {
      this.$emit("selectFilterBuildYear", year);
    },
  },
};
</script>

<style lang="scss" scoped>
.md-list {
  width: 100%;
  max-width: 100%;
  padding: 0;
  display: inline-block;
  vertical-align: top;
  border: 1px solid rgba(#000, 0.12);
}
.md-list .default-font-size {
  font-size: inherit !important;
}
.md-list .center {
  text-align: center !important;
}

#filterButton {
  position: absolute;
  top: 180px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
  padding: 5px;
  width: 70px;
  height: 35px;
}

#filter {
  position: absolute;
  top: 230px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
  padding: 5px;
  width: 300px;
  height: 302px;
}

#filter .filter-title {
  font-size: 18px;
  font-weight: bold;
  color: coral;
}

#filter button {
  width: 80px;
  height: 35px;
  font-size: 12px;
}
</style>
