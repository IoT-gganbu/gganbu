import { api } from "@/store";

const memberStore = {
  state: {
    isLogin: false,
<<<<<<< HEAD
    memberList: undefined,
=======
    memberList: [],
    selectMember: 0,
>>>>>>> bbfba43ba4c042171bb58202c2af7f4e68f93ae2
  },
  getters: {
    isLogin(state) {
      console.log(state.isLogin);
      return JSON.parse(sessionStorage.getItem("isLogin"));
    },
    getMemberListAll(state, payload) {
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
<<<<<<< HEAD
    GET_MEMBER_LIST_ALL(state, payload) {
      state.memberList = payload;
=======
    SAVE_MEMBER_LIST(state, payload) {
      state.memberList = payload;
    },
    SET_SELECTED_MEMBER(state, payload) {
      state.selectMember = payload;
    },
  },
  actions: {
    GET_MEMBER_LIST_ALL({ commit }) {
      console.log("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
      this.$axios
        .get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`)
        .then((response) => {
          commit("SAVE_MEMBER_LIST", response.data);
          console.log("ZAZAXSAZXAS");
        })
        .catch((error) => console.log(error));
>>>>>>> bbfba43ba4c042171bb58202c2af7f4e68f93ae2
    },
  },
};
export default memberStore;
