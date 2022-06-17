<template>
  <div>
    <parallax class="section page-header header-filter" :style="headerStyle">
      <div class="map_wrap">
        <!-- 지도 -->
        <div
          id="map"
          style="width:100%;height:100%;position:relative;overflow:hidden;"
        ></div>

        <!-- 시도/구군/동 선택창 -->
        <area-select @moveMapByAddress="moveMapByAddress"></area-select>

        <!-- 주소 검색창 -->
        <area-search @moveMapByPos="moveMapByPos"></area-search>

        <!-- 카테고리 검색 -->
        <category-search ref="categorySearch" :map="map"></category-search>

        <!-- 필터 -->
        <house-filter
          @resetFilter="resetFilter"
          @selectFilterHouseType="selectFilterHouseType"
          @selectFilterBuildYear="selectFilterBuildYear"
          :filterHouseType="filterHouseType"
          :filterBuildYear="filterBuildYear"
        ></house-filter>

        <!-- 상세정보창 -->
        <house-detail></house-detail>
      </div>
    </parallax>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { KAKAO_MAP_JAVASCRIPT_KEY } from "@/config";
import AreaSelect from "@/components/map/AreaSelect.vue";
import AreaSearch from "@/components/map/AreaSearch.vue";
import CategorySearch from "@/components/map/CategorySearch.vue";
import HouseFilter from "@/components/map/HouseFilter.vue";
import HouseDetail from "@/components/map/HouseDetail.vue";

const houseStore = "houseStore";

export default {
  bodyClass: "landing-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/bg7.jpg"),
    },
  },
  components: {
    AreaSelect,
    AreaSearch,
    CategorySearch,
    HouseFilter,
    HouseDetail,
  },
  data() {
    return {
      map: null, // 카카오맵
      houseMarkers: [], // 집 마커

      filterHouseType: "all",
      filterBuildYear: 0,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        // "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&libraries=services&appkey=c5dd7f232900f6b19f29c968f087bb0f";
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&libraries=services&appkey=" +
        KAKAO_MAP_JAVASCRIPT_KEY;
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      // 1. 지도 등록
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);

      // 2. 카테고리 검색을 위한 설정
      // 지도에 idle 이벤트를 등록합니다
      window.kakao.maps.event.addListener(
        this.map,
        "idle",
        this.$refs.categorySearch.searchPlaces
      );
      this.$refs.categorySearch.init();

      // 지도 중심좌표 변경 이벤트 등록
      kakao.maps.event.addListener(this.map, "center_changed", () => {
        // 지도의 중심좌표를 얻어옵니다
        var coords = this.map.getCenter();
        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();
        geocoder.coord2RegionCode(
          coords.getLng(),
          coords.getLat(),
          this.getCenterAroundHouseInfo
        );
      });

      // 메인 페이지에서 검색해서 넘어왔으면 해당 좌표로 이동
      // console.log(this.$route.params.lat);
      if (this.$route.params.lat) {
        let pos = new kakao.maps.LatLng(
          this.$route.params.lat,
          this.$route.params.lng
        );

        // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
        this.map.panTo(pos);
      } else {
        // 아니면 현재 위치받아서 이동시킨다.
        navigator.geolocation.getCurrentPosition(
          this.locationLoadSuccess,
          this.locationLoadError
        );
      }
    },

    /////// 최초 맵 로드시 현재위치로 지도 이동
    // 현재위치를 받아오기 성공시
    locationLoadSuccess(pos) {
      // 현재 위치 받아오기
      let currentPos = new kakao.maps.LatLng(
        pos.coords.latitude,
        pos.coords.longitude
      );

      // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
      this.map.panTo(currentPos);
    },

    // 현재위치를 받아오기 실패시
    locationLoadError() {
      console.log("위치 정보를 가져오는데 실패했습니다.");
    },

    //////// 주소 및 좌표로 지도 이동
    // 주소로 지도 이동
    moveMapByAddress(address) {
      new kakao.maps.services.Geocoder().addressSearch(
        address,
        (result, status) => {
          if (status === kakao.maps.services.Status.OK) {
            // 이동할 위도 경도 위치를 생성합니다
            var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도 중심을 부드럽게 이동시킵니다
            // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
            this.map.panTo(moveLatLon);
          }
        }
      );
    },

    // 좌표로 지도 이동
    moveMapByPos(lat, lng) {
      let pos = new kakao.maps.LatLng(lat, lng);

      // 지도 이동(기존 위치와 가깝다면 부드럽게 이동)
      this.map.panTo(pos);
    },

    //////// 지도 검색 관련
    // 검색 필터 초기화
    resetFilter() {
      this.filterHouseType = "all";
      this.filterBuildYear = 0;
      this.refreshHouseList();
    },

    // 거래유형 필터
    selectFilterHouseType(type) {
      this.filterHouseType = type;
      this.refreshHouseList();
    },

    // 건축년도 필터
    selectFilterBuildYear(year) {
      this.filterBuildYear = year;
      this.refreshHouseList();
    },

    // 지도에 보여지는 집 목록 갱신
    refreshHouseList() {
      // 지도의 중심좌표를 얻어옵니다
      var coords = this.map.getCenter();
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.coord2RegionCode(
        coords.getLng(),
        coords.getLat(),
        this.getCenterAroundHouseInfo
      );
    },

    // 지도가 이동할 때 해당 법정동 코드를 받아서 백엔드 api 호출하여 해당 동 데이터만 받아옴
    async getCenterAroundHouseInfo(result, status) {
      if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < result.length; i++) {
          // 법정동
          if (result[i].region_type === "B") {
            await this.getHouseList({
              dongCode: result[i].code,
              filterType: this.filterHouseType,
              filterBuildYear: this.filterBuildYear,
            }); // 동데이터 요청
          }
        }
      }
    },

    // 지도의 집 마커 제거
    deleteHouseMarkers() {
      for (var i = 0; i < this.houseMarkers.length; i++) {
        this.houseMarkers[i].setMap(null);
      }
      this.houseMarkers = [];
    },

    ...mapActions(houseStore, ["getHouseList", "selectHouse", "initHouse"]),
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
    ...mapState(houseStore, ["houses"]),
  },
  watch: {
    // 집 정보가 갱신되면
    houses(newHouses) {
      this.deleteHouseMarkers();
      for (var i = 0; i < newHouses.length; i++) {
        // 마커를 생성합니다
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(newHouses[i].lat, newHouses[i].lng),
          title: newHouses[i].houseName,
          clickable: true,
        });

        // 마커에 클릭 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", () => {
          // 눌렀을 때 하우스 정보 세팅
          this.selectHouse(marker.getTitle());
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(this.map);
        this.houseMarkers.push(marker);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.section {
  padding: 70px 0 0 0;
}
.header-filter:after {
  width: 0;
  height: 0;
}
</style>
<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 100%;
}

.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px !important;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff !important;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
