import { api } from "@/store";

const memberStore = {
  state: {
    isLogin: false,
    memberList: [],
  },
  getters: {
    isLogin(state) {
      console.log(state.isLogin);
      return JSON.parse(sessionStorage.getItem("isLogin"));
    },
    getMemberListAll(state, payload) {
      console.log("11");
      return state.memberList.data[payload];
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
    SAVE_MEMBER_LIST(state, payload) {
      state.memberList = payload;
    },
  },
  actions: {},
};
export default memberStore;
