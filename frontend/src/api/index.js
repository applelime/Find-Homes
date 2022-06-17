import axios from "axios";
import { API_BASE_URL, KAKAO_REST_API_URL } from "@/config";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

function kakaoMapApiInstance() {
  const instance = axios.create({
    baseURL: KAKAO_REST_API_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance, kakaoMapApiInstance };
