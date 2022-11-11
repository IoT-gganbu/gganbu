import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
import sockjs from "sockjs-client";
import Stomp from "webstomp-client";
// import axios from "axios";

Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
    baseurl: "http://127.0.0.1:8000/",
    springWebsocketUrl: "http://127.0.0.1:8080/ws/spring",
    patientId: "",
    patient: {},
    progressBoolean: [false, false, false, false, false, false, false, false, false, false],
    springSocket: null,
    springStomp: null,
    springSocketConnected: false,
    springSocketMessage: "",
    isChecked: false,
  },
  getters: {
    getSpringWebsocketUrl: (state) => {
      return state.springWebsocketUrl;
    },
    getPatientId: (state) => {
      return state.patientId;
    },
    getPatient: (state) => {
      return state.patient;
    },
    getSpringSocket: (state) => {
      return state.springSocket;
    },
    getSpringSocketConnected: (state) => {
      return state.springSocketConnected;
    },
    getSpringSocketMessage: (state) => {
      return state.springSocketMessage;
    },
    getProgressBoolean: (state) => {
      return state.progressBoolean;
    },
  },
  mutations: {
    changePatientId(state, patientId) {
      state.patientId = patientId;
    },
    changePatient(state, patient) {
      state.patient = patient;
    },
    changeSpringSocket(state, springSocket) {
      state.springSocket = springSocket;
    },
    changeSpringSocketConnected(state, bool) {
      state.springSocketConnected = bool;
    },
    changeSpringSocketMessage(state, springSocketMessage) {
      state.springSocketMessage = springSocketMessage;
    },
    acceptProgressBoolean(state, idx) {
      state.progressBoolean[idx] = true;
    },
    changeChecked(state) {
      state.isChecked = !state.isChecked;
    },
  },
  actions: {
    async setPatientId({ commit }, patientId) {
      commit("changePatientId", patientId);
    },
    async setPatient({ commit }, patient) {
      commit("changePatient", patient);
    },
    async setSpringSocketConnected({ commit }, bool) {
      commit("changeSpringSocketConnected", bool);
    },
    async connectSpringSocket({ state }) {
      state.springSocket = new sockjs(state.springWebsocketUrl);
      state.springStomp = Stomp.over(state.springSocket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ` + this.state.springWebsocketUrl);
      state.springStomp.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.commit("changeSpringSocketConnected", true);
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.state.springStomp.subscribe("/ros/" + this.state.patientId, (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 state에 저장합니다.
            this.commit("changeSpringSocketMessage", JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    async acceptProgressBoolean({ commit }, idx) {
      commit("acceptProgressBoolean", idx, true);
    },
  },
  modules: {
    // memberStore,
  },
});
