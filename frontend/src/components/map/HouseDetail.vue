<template>
  <!-- 우측 상세정보창 -->
  <div>
    <md-content id="result" v-if="house">
      <md-list-item>
        <div @click="initHouse()">
          <b-icon-arrow-left
            style="width:20px; height:20px"
          ></b-icon-arrow-left>
        </div>
        <div v-if="userInfo">
          <b-icon
            @click="addHouseInterest()"
            v-if="!checkHouseInterest()"
            icon="bookmark-heart"
            style="width:20px; height:20px"
          ></b-icon>
          <b-icon
            @click="removeHouseInterest()"
            v-if="checkHouseInterest()"
            icon="bookmark-heart-fill"
            style="width:20px; height:20px"
          ></b-icon>
        </div>
      </md-list-item>
      <div class="result-title" v-text="house.houseName">아파트명</div>
      <md-list>
        <md-list-item>
          <div style="width: 60px;">건축년도</div>
          <div class="md-list-item-text">{{ house.buildYear }}</div>
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <div style="width: 60px;">주소</div>
          <div class="md-list-item-text">
            {{ house.sigungu }} {{ house.roadName }}
          </div>
        </md-list-item>
        <md-divider></md-divider>
        <md-list-item>
          <div class="md-list-item-text">실거래가</div>
          <!-- 전체, 매매, 전월세 필터링 -->
          <div>
            <b-button
              variant="outline-secondary"
              style="width: 60px; height: 30px; margin-left: 5px;"
              @click="selectFilterHouseDealType('all')"
              :pressed="filterHouseDealType == 'all'"
              >전체</b-button
            >
            <b-button
              variant="outline-secondary"
              style="width: 60px; height: 30px; margin-left: 5px;"
              @click="selectFilterHouseDealType('sale')"
              :pressed="filterHouseDealType == 'sale'"
              >매매</b-button
            >
            <b-button
              variant="outline-secondary"
              style="width: 60px; height: 30px; margin-left: 5px;"
              @click="selectFilterHouseDealType('etc')"
              :pressed="filterHouseDealType == 'etc'"
              >전월세</b-button
            >
          </div>
        </md-list-item>
        <md-table>
          <md-table-row class="default-font-size center">
            <md-table-cell>계약일</md-table-cell>
            <md-table-cell>거래가격</md-table-cell>
            <md-table-cell>종류</md-table-cell>
            <md-table-cell>면적</md-table-cell>
            <md-table-cell>층수</md-table-cell>
          </md-table-row>

          <md-table-row
            v-for="houseDeal in filteredHouseDeals"
            :key="houseDeal.dealId"
            class="center"
          >
            <md-table-cell
              >{{ houseDeal.dealYear }}.{{ houseDeal.dealMonth }}.{{
                houseDeal.dealDay
              }}</md-table-cell
            >
            <md-table-cell>{{
              houseDeal.dealType == "sale"
                ? houseDeal.saleAmount
                : houseDeal.dealType == "jeonse"
                ? houseDeal.jeonseAmount
                : houseDeal.monthlyDeposit + "/" + houseDeal.monthlyLease
            }}</md-table-cell>
            <md-table-cell>{{
              houseDeal.dealType == "sale"
                ? "매매"
                : houseDeal.dealType == "jeonse"
                ? "전세"
                : "월세"
            }}</md-table-cell>
            <md-table-cell>{{ houseDeal.area }}m²</md-table-cell>
            <md-table-cell>{{ houseDeal.floor }}층</md-table-cell>
          </md-table-row>
        </md-table>
      </md-list>
    </md-content>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const interestStore = "interestStore";
const memberStore = "memberStore";
const houseStore = "houseStore";
export default {
  components: {},
  props: ["filterHouseType", "filterBuildYear"],
  data() {
    return {
      houseInterestInfo: null, // 선택한 집의 관심데이터 정보
      filterHouseDealType: "all", // 선택한 집 거래종류 필터링
      filteredHouseDeals: [], // 필터링된 집 거래정보
    };
  },
  created() {
    this.filterHouseDealType = "all";
  },
  methods: {
    ...mapActions(houseStore, ["initHouse"]),
    ...mapActions(interestStore, ["addInterestHouse", "deleteInterestHouse"]),

    // 현재 집이 관심매물인지 체크
    checkHouseInterest() {
      for (let i = 0; i < this.interestHouseList.length; i++) {
        if (this.interestHouseList[i].house_id == this.house.houseId) {
          this.houseInterestInfo = this.interestHouseList[i];
          return true;
        }
      }
      for (let i = 0; i < this.interestOfficetelList.length; i++) {
        if (this.interestOfficetelList[i].house_id == this.house.houseId) {
          this.houseInterestInfo = this.interestOfficetelList[i];
          return true;
        }
      }
      return false;
    },

    // 관심매물 등록
    addHouseInterest() {
      this.addInterestHouse({
        userid: this.userInfo.userid,
        houseid: this.house.houseId,
      });
    },

    // 관심매물 제거
    removeHouseInterest() {
      this.deleteInterestHouse(this.houseInterestInfo);
    },

    // 매물 거래 타입 필터링
    selectFilterHouseDealType(type) {
      this.filterHouseDealType = type;
      // 눌렀을 때 하우스 정보 세팅
      if (type == "all") {
        this.filteredHouseDeals = this.houseDeals;
        return;
      }

      let tempDeals = [];
      for (let i = 0; i < this.houseDeals.length; i++) {
        if (type == "sale") {
          if (this.houseDeals[i].dealType == "sale") {
            tempDeals.push(this.houseDeals[i]);
          }
        } else {
          if (this.houseDeals[i].dealType != "sale") {
            tempDeals.push(this.houseDeals[i]);
          }
        }
      }

      // console.log(tempDeals);
      this.filteredHouseDeals = tempDeals;
    },
  },

  computed: {
    ...mapState(interestStore, ["interestHouseList", "interestOfficetelList"]),
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(houseStore, ["house", "houseDeals"]),
  },
  watch: {
    houseDeals(newHouseDeals) {
      this.filteredHouseDeals = newHouseDeals;
      // console.log(this.filteredHouseDeals);
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

#result {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 450px;
  height: 100%;
  border-radius: 0px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: auto;
  z-index: 2;
}
#result .result-title {
  font-weight: bold;
  font-size: 30px;
  padding: 15px 15px;
  color: black;
  line-height: initial;
  border: 1px solid rgba(#000, 0.12);
}

#result button {
  font-size: 12px;
}
</style>
