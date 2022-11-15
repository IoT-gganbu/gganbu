import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: "https://k7b309.p.ssafy.io/api",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  });
}

export { createApi };
