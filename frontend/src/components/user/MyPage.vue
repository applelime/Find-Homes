<template>
  <div class="wrapper">
    <parallax
      class="section page-header header-filter"
      :style="headerStyle"
    ></parallax>
    <div class="main main-raised">
      <div class="section profile-content">
        <div class="container">
          <div class="md-layout">
            <div class="md-layout-item md-size-50 mx-auto">
              <div class="profile">
                <div class="avatar">
                  <img
                    :src="img"
                    alt="Circle Image"
                    class="img-raised rounded-circle img-fluid"
                  />
                </div>
                <br />
                <div class="name">
                  <md-card class="md-primary" md-theme="green-card">
                    <md-card-header>
                      <md-card-header-text>
                        <div class="md-title">마이페이지</div>
                      </md-card-header-text>
                    </md-card-header>
                    <md-card-content>
                      <md-field>
                        <label>id</label>
                        <md-input v-model="userInfo.userid" disabled></md-input>
                      </md-field>

                      <md-field>
                        <label>Nickname</label>
                        <md-input
                          v-model="userInfo.nickname"
                          disabled
                        ></md-input>
                      </md-field>
                      <md-field>
                        <label>Phone</label>
                        <md-input v-model="userInfo.phone" disabled></md-input>
                      </md-field>

                      <md-field>
                        <label>Email</label>
                        <md-input v-model="userInfo.email" disabled></md-input>
                      </md-field>
                      <br />

                      <b-button
                        variant="warning mr-3"
                        @click="userUpdate = true"
                        >수정</b-button
                      >
                      <b-button variant="danger" @click="deleteUser()"
                        >탈퇴</b-button
                      >
                    </md-card-content>
                    <modal v-if="userUpdate" @close="modalHide">
                      <template slot="header">
                        <h4 class="modal-title">
                          {{ userInfo.userid }}님의 정보 수정하기
                        </h4>
                        <md-button
                          class="md-simple md-just-icon md-round modal-default-button"
                          @click="modalHide"
                        >
                          <md-icon>clear</md-icon>
                        </md-button>
                      </template>

                      <template slot="body">
                        <md-field>
                          <label>Phone</label>
                          <md-input v-model="userInfo.phone"></md-input>
                        </md-field>
                        <md-field>
                          <label>Password</label>
                          <md-input v-model="userInfo.userpw"></md-input>
                        </md-field>
                      </template>

                      <template slot="footer">
                        <md-button class="md-simple md-success" @click="update"
                          >수정하기</md-button
                        >
                        <md-button
                          class="md-danger md-simple"
                          @click="modalHide"
                          >닫기</md-button
                        >
                      </template>
                    </modal>
                  </md-card>
                </div>
              </div>
            </div>
          </div>
          <div class="profile-tabs">
            <div class="description text-center">
              <h3>관심 매물 현황</h3>
            </div>
            <tabs
              :tab-name="['ApartMent', 'Offictel']"
              :tab-icon="['home', 'apartment']"
              plain
              nav-pills-icons
              color-button="success"
            >
              <!-- here you can add your content for tab-content -->
              <template slot="tab-pane-1">
                <div class="md-layout">
                  <interest-house-card
                    class=" md-size-20 md-layout-item"
                    v-for="(item, index) in this.interestHouseList"
                    :key="index"
                    :house="item"
                  ></interest-house-card>
                </div>
              </template>
              <template slot="tab-pane-2">
                <div class="md-layout">
                  <interest-house-card
                    class="md-size-20 md-layout-item"
                    v-for="(item, index) in this.interestOfficetelList"
                    :key="index"
                    :house="item"
                  ></interest-house-card>
                </div>
              </template>
            </tabs>
          </div>
          <!-- <ul v-if="this.interestHouseList !== null">
            <li v-for="(item, index) in this.interestHouseList" :key="index">
              <interest-house-card v-bind="item"></interest-house-card>
            </li>
          </ul>
          <ul>
            <li v-for="(item, index) in this.interestHouseList" :key="index">
              {{ item.houseid }}
            </li>
          </ul> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Tabs, Modal } from "@/components";
import { mapState, mapActions, mapMutations } from "vuex";
import InterestHouseCard from "./item/interestHouseCard.vue";
// import interestHouseCard from "@/components/user/item/interestHouseCard.vue";

const memberStore = "memberStore";
const interestStore = "interestStore";
export default {
  components: {
    Tabs,
    Modal,
    InterestHouseCard,
  },
  bodyClass: "profile-page",
  data() {
    return {
      userUpdate: false,
      aptList: null,
    };
  },

  props: {
    header: {
      type: String,
      default: require("@/assets/img/city-profile.jpg"),
    },
    img: {
      type: String,
      default: require("@/assets/img/faces/christian.jpg"),
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(interestStore, [
      "interestHouseList",
      "interestOfficetelList",
      "userid",
    ]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  methods: {
    ...mapActions(memberStore, ["updateOneUser", "deleteOneUser"]),
    ...mapActions(interestStore, [
      "interestAptList",
      "addInterestHouse",
      "deleteInterestHouse",
      "setUserId",
    ]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapMutations(interestStore, [
      "SET_APT_LIST",
      "SET_OFFICE_LIST",
      "SET_USERID",
    ]),
    deleteInterest(num) {
      this.deleteInterestHouse(num);
    },
    modalHide() {
      this.userUpdate = false;
    },
    async deleteUser() {
      await this.deleteOneUser(this.userInfo.userid);
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      this.SET_OFFICE_LIST(null);
      this.SET_APT_LIST(null);
      this.SET_USERID(null);
      sessionStorage.removeItem("access-token");

      this.$router.push({ name: "home" });
    },
    async update() {
      await this.updateOneUser(this.userInfo);
      this.modalHide();
    },
  },
  async mounted() {
    this.setUserId(this.userInfo.userid);
    let apt = { userid: this.userInfo.userid, type: "apt" };
    await this.interestAptList(apt);
    let office = { userid: this.userInfo.userid, type: "office" };
    await this.interestAptList(office);
  },
};
</script>

<style lang="scss" scoped>
.section {
  padding: 0;
}

.profile-tabs::v-deep {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    img {
      margin-bottom: 2.142rem;
    }
  }
}
.md-list {
  width: 320px;
  max-width: 100%;
  display: inline-block;
  vertical-align: top;
  border: 1px solid rgba(#000, 0.12);
}
</style>
