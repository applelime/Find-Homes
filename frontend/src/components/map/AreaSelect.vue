<template>
  <div id="area">
    <b-form-select
      class="sel"
      name="sido"
      id="sido"
      v-model="sidoCode"
      :options="sidos"
      @change="gugunList"
    >
      <template #first>
        <b-form-select-option value="" disabled>시/도</b-form-select-option>
      </template>
    </b-form-select>
    <b-form-select
      class="sel"
      name="gugun"
      id="gugun"
      v-model="gugunCode"
      :options="guguns"
      @change="dongList"
    >
      <template #first>
        <b-form-select-option value="" disabled>구/군</b-form-select-option>
      </template>
    </b-form-select>
    <b-form-select
      class="sel"
      name="dong"
      id="dong"
      v-model="dongCode"
      :options="dongs"
      @change="moveMapByDong"
    >
      <template #first>
        <b-form-select-option value="" disabled>동</b-form-select-option>
      </template>
    </b-form-select>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from "vuex";

const houseStore = "houseStore";
export default {
  components: {},
  data() {
    return {
      sidoCode: "",
      gugunCode: "",
      dongCode: "",
    };
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getDong"]),

    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),

    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },

    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },

    moveMapByDong() {
      if (!this.dongCode) return;

      // 선택한 동으로 좌표 이동
      let sidoName = this.sidos.find((item) => item.value == this.sidoCode)
        .text;
      let gugunName = this.guguns.find((item) => item.value == this.gugunCode)
        .text;
      let dongName = this.dongs.find((item) => item.value == this.dongCode)
        .text;

      let address = `${sidoName} ${gugunName} ${dongName}`;
      this.$emit("moveMapByAddress", address);
    },
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs"]),
  },
};
</script>

<style lang="scss" scoped>
#area {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: auto;
  z-index: 2;
}

#area .sel {
  width: 200px;
  height: 35px;
  border: 0;
}
</style>
