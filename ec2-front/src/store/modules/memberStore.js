import { api } from "@/store";

const memberStore = {
  state: {
    isLogin: false,
    memberList: undefined,
  },
  getters: {
    isLogin(state) {
      console.log(state.isLogin);
      return JSON.parse(sessionStorage.getItem("isLogin"));
    },
  },
  mutations: {
    MEMBER_LOGOUT(state) {
      console.log(sessionStorage.getItem("isLogin"));
      sessionStorage.setItem("isLogin", false);
      state.isLogin = false;
      sessionStorage.removeItem("access-token");
      api.defaults.headers["access-token"] = "";
    },
    GET_MEMBER_LIST_ALL(state, payload) {
      state.memberList = payload;
    },
  },
  actions: {},
};
export default memberStore;
