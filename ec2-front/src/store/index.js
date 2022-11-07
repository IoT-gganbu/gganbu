import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
import memberStore from "@/store/modules/memberStore";

Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
    baseurl: process.env.VUE_APP_API_SERVER,
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    memberStore,
  },
});
