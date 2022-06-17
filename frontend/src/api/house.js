import { apiInstance, kakaoMapApiInstance } from "./index.js";
import { KAKAO_MAP_REST_KEY } from "@/config";

const api = apiInstance();
const kakaoMapApi = kakaoMapApiInstance();

function sidoList(success, fail) {
  api
    .get(`/area/sido`)
    .then(success)
    .catch(fail);
}

function gugunList(sidoCode, success, fail) {
  api
    .get(`/area/gugun/${sidoCode}`)
    .then(success)
    .catch(fail);
}

function dongList(gugunCode, success, fail) {
  api
    .get(`/area/dong/${gugunCode}`)
    .then(success)
    .catch(fail);
}

function houseList(dongCode, filterType, filterBuildYear, success, fail) {
  api
    .get(`/house/dong/${dongCode}`, {
      params: {
        type: filterType,
        year: filterBuildYear,
      },
    })
    .then(success)
    .catch(fail);
}

function houseDealList(sigungu, houseName, success, fail) {
  console.log(sigungu, houseName);
  api
    .get(`/house/deal`, {
      params: {
        sigungu,
        "house-name": houseName,
      },
    })
    .then(success)
    .catch(fail);
}

function searchWordList(word, success, fail) {
  kakaoMapApi
    .get(`/v2/local/search/address.json`, {
      params: {
        page: 1,
        size: 3,
        query: word,
      },
      headers: {
        Authorization: `KakaoAK ${KAKAO_MAP_REST_KEY}`,
      },
    })
    .then(success)
    .catch(fail);
}

export {
  sidoList,
  gugunList,
  dongList,
  houseList,
  houseDealList,
  searchWordList,
};
