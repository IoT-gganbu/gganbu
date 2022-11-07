import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
// import memberStore from "@/store/modules/memberStore";

Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
<<<<<<< HEAD
    baseurl: "http://127.0.0.1:8000/",
=======
    qrCodeData: "",
>>>>>>> 391c28d25c491531c99c1c20301c239c1436d0bf
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    // memberStore,
  },
});
