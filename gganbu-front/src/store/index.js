import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
// import memberStore from "@/store/modules/memberStore";

Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
    baseurl: "http://127.0.0.1:8000/",
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    // memberStore,
  },
});
