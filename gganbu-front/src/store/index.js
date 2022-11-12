import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
import sockjs from "sockjs-client";
import Stomp from "webstomp-client";
import * as ROSLIB from "roslib";
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
    rosSocket: null,
    rosTopic: null,
    rosMessage: null,
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
    // 스프링 소켓(sockJ)
    async connectSpringSocket({ state }) {
      state.springSocket = new sockjs(state.springWebsocketUrl);
      state.springStomp = Stomp.over(state.springSocket);
      console.log(`스프링소켓 연결을 시도합니다. 서버 주소: ` + this.state.springWebsocketUrl);
      state.springStomp.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.commit("changeSpringSocketConnected", true);
          console.log("스프링소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.state.springStomp.subscribe("/ros/" + this.state.patientId, (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);
            // 받은 데이터를 기반으로 화면을 업데이트 합니다.
            let data = JSON.parse(res.body);
            // data 예시 {"patientId":"10","task":"3"}
            for (let i = 0; i < data.task; i++) {
              if (this.state.progressBoolean[i] == false) {
                this.commit("acceptProgressBoolean", i);
              }
            }
            console.log(this.state.progressBoolean);
            // 받은 데이터를 json으로 파싱하고 state에 저장합니다.
            this.commit("changeSpringSocketMessage", data);
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("스프링소켓 연결 실패", error);
          this.connected = false;
        }
      );
      // state.springSocket.onclose = () => {
      //   console.log("스프링소켓 연결이 끊겼습니다.");
      //   this.commit("changeSpringSocketConnected", false);
      // };
    },
    // 스프링 소켓을 연결 해제한다.
    async disconnectSpringSocket({ state }) {
      if (state.springStomp != null) {
        state.springStomp.disconnect();
        console.log("스프링 스톰프소켓 연결 해제");
        this.commit;
      }
      state.springSocket.onclose = () => {
        console.log("스프링소켓 연결이 끊었습니다.");
        this.commit("changeSpringSocketConnected", false);
      };
      this.commit("changeSpringSocketConnected", false);
    },
    async acceptProgressBoolean({ commit }, idx) {
      commit("acceptProgressBoolean", idx, true);
    },
    async connectRosSocket({ state }) {
      state.rosSocket = new ROSLIB.Ros({
        url: "ws://localhost:9090",
      });
      state.rosSocket.on("connection", () => {
        console.log("Connected to RosSocket server.");
      });
      state.rosSocket.on("error", (error) => {
        console.log("Error connecting to RosSocket server: ", error);
      });
      state.rosSocket.on("close", () => {
        console.log("Connection to RosSocket server closed.");
      });
      state.rosTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/turtle1/cmd_vel",
        messageType: "geometry_msgs/Twist",
      });
      state.rosMessage = new ROSLIB.Message({
        linear: {
          x: 1.0,
          y: 0.0,
          z: 0.0,
        },
        angular: {
          x: 0.0,
          y: 0.0,
          z: 1.0,
        },
      });
    },
    async publishRosSocket() {
      this.state.rosTopic.publish(this.state.rosMessage);
    },
    async disconnectAllsocket({ state }) {
      if (state.springStomp != null) {
        await state.springStomp.disconnect();
        console.log("스프링 스톰프소켓 연결 해제");
        this.state.springSocket = null;
        this.state.springStomp = null;
        this.state.springSocketConnected = false;
        this.state.springSocketMessage = null;
      }
      if (state.rosSocket != null) {
        await state.rosSocket.close();
        console.log("ros소켓 연결 해제");
        this.state.rosSocket = null;
        this.state.rosMessage = null;
      }
      console.log("every socket disconnected");
    },
  },
  modules: {
    // memberStore,
  },
});
