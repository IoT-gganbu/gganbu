import axios from "axios";

function createApi() {
  return axios.create({
    baseURL: "http://127.0.0.1:0/api",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  });
}

export { createApi };
