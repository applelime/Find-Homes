<template>
  <div @click="moveMap()">
    <md-card-media>
      <img src="@/assets/img/bg3.jpg" alt="" />
    </md-card-media>

    <md-card-header>
      <div class="md-title" style="padding: 0px 10px;">
        <span style="vertical-align: middle;">{{ house.house_name }}</span>
        <b-button
          variant="outline-danger"
          style="width: 25px; height: 25px; padding: 0px; border: 0px;"
          @click="deleteHouseFromList()"
        >
          <b-icon icon="dash-circle"></b-icon>
        </b-button>
      </div>
      <div class="md-subhead" style="padding:5px 10px 0px 10px;">
        {{ house.sigungu }} {{ house.road_name }}
      </div>
    </md-card-header>
  </div>
</template>

<script>
import { mapActions } from "vuex";
const interestStore = "interestStore";
export default {
  name: "interestHouseCard",
  data() {
    return {
      num: null,
      isRemove: false,
    };
  },

  props: {
    house: Object,
  },
  mounted() {},
  methods: {
    ...mapActions(interestStore, ["deleteInterestHouse"]),
    deleteHouseFromList() {
      console.log(this.house.num);
      this.deleteInterestHouse(this.house);
      this.isRemove = true;
    },
    moveMap() {
      if (this.isRemove) {
        this.isRemove = false;
        return;
      }
      this.$router.push({
        name: "map",
        params: {
          lng: this.house.lng,
          lat: this.house.lat,
        },
      });
    },
  },
};
</script>

<style></style>
