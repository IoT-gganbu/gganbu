import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";

Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
    baseurl: "http://127.0.0.1:8000/",
    patientId: "",
  },
  getters: {},
  mutations: {},
  actions: {
    // insertQrcode({commit, state}) {
    // }
  },
  modules: {
    // memberStore,
  },
});
