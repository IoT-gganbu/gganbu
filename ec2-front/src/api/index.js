import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: process.env.VUE_APP_API_SERVER,
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  });
}

export { createApi };
