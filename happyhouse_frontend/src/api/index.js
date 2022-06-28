import axios from "axios";
import { API_BASE_URL, APT_DEAL_URL } from "@/config";

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

function houseInstance() {
  const instance = axios.create({
    baseURL: APT_DEAL_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

// function newsInstance() {
//   const instance = axios.create({
//     baseURL: NAVER_NEWS_URL,
//     headers: {
//       Accept: "*/*",
//       "Content-type": "application/json",
//       "X-Naver-Client-Id": "h41KtiWMWc2FvxWiL41h",
//       "X-Naver-Client-Secret": "VvSy0Q3Epd",
//     },
//   });
//   return instance;
// }

export { apiInstance, houseInstance };
