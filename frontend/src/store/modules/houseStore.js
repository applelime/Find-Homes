import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  houseDealList,
  searchWordList,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [],
    guguns: [],
    dongs: [],
    houses: [],
    house: null,
    houseDeals: [],

    searchResultList: [],
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sido_code, text: sido.sido_name });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugun_code, text: gugun.gugun_name });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dong_code, text: dong.dong_name });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_HOUSE_DEAL_LIST: (state, houseDeals) => {
      state.houseDeals = houseDeals;
    },
    SET_HOUSE: (state, house) => {
      state.house = house;
    },
    INIT_HOUSE: (state) => {
      state.house = null;
    },
    SET_SEARCH_WORD_LIST: (state, data) => {
      state.searchResultList = data.documents;
    },
    INIT_SEARCH_WORD_LIST: (state) => {
      state.searchResultList = [];
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      gugunList(
        sidoCode,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      dongList(
        gugunCode,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, data) => {
      houseList(
        data.dongCode,
        data.filterType,
        data.filterBuildYear,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    selectHouse: ({ commit, state }, houseName) => {
      let house = null;
      let sigungu = "";
      for (let i = 0; i < state.houses.length; i++) {
        const el = state.houses[i];
        if (el.houseName === houseName) {
          house = el;
          sigungu = el.sigungu;
          break;
        }
      }
      commit("SET_HOUSE", house);

      // 선택한 집의 deal 정보도 받아오기
      houseDealList(
        sigungu,
        houseName,
        (response) => {
          commit("SET_HOUSE_DEAL_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    initHouse: ({ commit }) => {
      commit("INIT_HOUSE");
    },

    // 메인화면에서 단어 검색
    getSearchWordList: ({ commit }, word) => {
      if (!word) {
        commit("INIT_SEARCH_WORD_LIST");
        return;
      }
      searchWordList(
        word,
        (response) => {
          commit("SET_SEARCH_WORD_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    initResultList: ({ commit }) => {
      commit("INIT_SEARCH_WORD_LIST");
    },
  },
};

export default houseStore;
