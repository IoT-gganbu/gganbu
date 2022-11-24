import Vue from "vue";
import Vuex from "vuex";
import { createApi } from "@/api";
import sockjs from "sockjs-client";
import Stomp from "webstomp-client";
import * as ROSLIB from "roslib";
// import axios from "axios";
import router from "@/router/index.js";
import axios from "axios";
Vue.use(Vuex);

export const api = createApi();

export default new Vuex.Store({
  state: {
    baseurl: "http://127.0.0.1:8000/",
    springWebsocketUrl: "http://k7b309.p.ssafy.io:8081/ws/spring",
    // springWebsocketUrl: "http://localhost:8081/ws/spring",
    patientId: "",
    patient: {},
    progressBoolean: 1,
    springSocket: null,
    springStomp: null,
    springSocketConnected: false,
    springSocketMessage: "",
    rosSocket: null,
    rosTopic: null,
    rosSubTopic: null,
    rosMessage: null,
    turtleSocket: null,
    turtleTopic: null,
    turtleSubTopic: null,
    turtleMessage: null,
    isChecked: false,
    progressName: ["진찰 및 문진표 작성", "기초 / 신체 계측", "채혈 / 소변", "흉부 방사선", "진찰 및 상담", "자궁경부암", "유방암", "위암", "대장암", "폐암"],
    tracking: false,
    voice: false,
    turtleData: [
      [0, 0],
      [1, 1],
      [1, 0],
    ],
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
    getTracking: (state) => {
      return state.tracking;
    },
    getVoice: (state) => {
      return state.voice;
    },
    getRosSocket: (state) => {
      return state.rosSocket;
    },
    getTurtleSocket: (state) => {
      return state.turtleSocket;
    },
    getTurtleXData: (state) => {
      return state.turtleData[state.progressBoolean][0];
    },
    getTurtleYData: (state) => {
      return state.turtleData[state.progressBoolean][1];
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
    changeProgressBoolean(state, idx) {
      state.progressBoolean = idx;
    },
    changeChecked(state) {
      state.isChecked = !state.isChecked;
    },
    changeTracking(state) {
      state.tracking = !state.tracking;
    },
    changeVoice(state) {
      state.voice = !state.voice;
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
            console.log("this task is " + data.task);
            // data 예시 {"patientId":"10","task":"3"}
            this.commit("changeProgressBoolean", data.task);
            // for (let i = 1; i < data.task; i++) {
            //   if (this.state.progressBoolean[i] == false) {
            //     this.commit("changeProgressBoolean", i);
            //   }
            // }
            // console.log(this.state.progressBoolean);
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
    async changeProgressBoolean({ commit }, idx) {
      commit("changeProgressBoolean", idx);
    },
    // ros 소켓 연결(다경)
    async connectRosSocket({ state }) {
      state.rosSocket = new ROSLIB.Ros({
        url: "ws://192.168.31.96:9090",
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
      // state.rosTopic = new ROSLIB.Topic({
      //   ros: this.state.rosSocket,
      //   name: "/step",
      //   messageType: "std_msgs::Int32",
      // });
      // state.rosMessage = new ROSLIB.Message({ data: 123 });
    },
    // turtlebot 소켓 연결(한기)
    async connectTurtleSocket({ state }) {
      state.turtleSocket = new ROSLIB.Ros({
        url: "ws://192.168.219.129:9090",
      });
      state.turtleSocket.on("connection", () => {
        console.log("Connected to RosSocket server.");
      });
      state.turtleSocket.on("error", (error) => {
        console.log("Error connecting to RosSocket server: ", error);
      });
      state.turtleSocket.on("close", () => {
        console.log("Connection to RosSocket server closed.");
      });
    },
    // ros topic 생성
    async createRosTopic({ state }, data) {
      state.rosTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/step",
        messageType: "std_msgs::Int32",
      });
      state.rosMessage = new ROSLIB.Message({ data: data });
    },
    // turtle x topic 생성
    async createTurtleXTopic({ state }, data) {
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/x",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: data });
    },
    // turtle y topic 생성
    async createTurtleYTopic({ state }, data) {
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/y",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: data });
    },
    // turtle stop topic 생성
    async createStopTurtleTopic({ state }) {
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/stop",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: 0 });
    },
    // ros 정지 topic 생성
    async createStopRosTopic({ state }, data) {
      state.rosTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/stop",
        messageType: "std_msgs::Int32",
      });
      state.rosMessage = new ROSLIB.Message({ data: data });
    },
    // ros topic 보내기
    async publishRosSocket() {
      console.log("this.state.rosMessage.data is " + this.state.rosMessage.data);
      this.state.rosTopic.publish(this.state.rosMessage);
    },
    // turtle topic 보내기
    async publishTurtleSocket() {
      console.log("this.state.turtleMessage.data is " + this.state.turtleMessage.name + this.state.turtleMessage.data);
      this.state.turtleTopic.publish(this.state.turtleMessage);
    },
    async testRosTopic({ state }, data) {
      console.log("test", data, "번 보냄");
      state.rosTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/step",
        messageType: "std_msgs::Int32",
      });
      state.rosMessage = new ROSLIB.Message({ data: data });
      this.state.rosTopic.publish(this.state.rosMessage);
    },
    async testTurtleTopic({ state }, data) {
      console.log("turtle 테스트", data, "보냄");
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/x",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: data });
      this.state.turtleTopic.publish(this.state.turtleMessage);
    },
    async testTurtleTopicY({ state }, data) {
      console.log("turtle 테스트", data, "보냄");
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/y",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: data });
      this.state.turtleTopic.publish(this.state.turtleMessage);
    },
    async testTurtleStopTopic({ state }, data) {
      console.log("turtle 테스트", data, "보냄");
      state.turtleTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/stop",
        messageType: "std_msgs/Int32",
      });
      state.turtleMessage = new ROSLIB.Message({ data: data });
      this.state.turtleTopic.publish(this.state.turtleMessage);
    },
    async testStopRosTopic({ state }, data) {
      console.log("test", data, "번 보냄");
      state.rosTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/stop",
        messageType: "std_msgs::Int32",
      });
      state.rosMessage = new ROSLIB.Message({ data: data });
      this.state.rosTopic.publish(this.state.rosMessage);
    },
    // ros topic 받기(SUCCESS받아서 홈화면으로 보내고 fast api 종료하기)
    async createSubRosTopic({ state }) {
      state.rosSubTopic = new ROSLIB.Topic({
        ros: this.state.rosSocket,
        name: "/listener",
        messageType: "std_msgs/String",
      });

      state.rosSubTopic.subscribe(function (message) {
        console.log("Received message from gazebo " + state.rosSubTopic.name + ": " + message.data);
        if (message.data == "SUCCESS") {
          // fast-api 로 트래킹이랑 깐부 인식 멈추기 api 하나 만들어서 보내기
          axios.post(state.baseurl + "stop").then((response) => {
            console.log(response);
          });
          router.push("/").catch(() => {});
        }
        state.rosSubTopic.unsubscribe();
      });
    },
    // turtle topic 받기(SUCCESS받아서 홈화면으로 보내고 fast api 종료하기)
    async createSubTurtleTopic({ state }) {
      state.turtleSubTopic = new ROSLIB.Topic({
        ros: this.state.turtleSocket,
        name: "/listener",
        messageType: "std_msgs/String",
      });

      state.turtleSubTopic.subscribe(function (message) {
        console.log("Received message from turtle " + state.turtleSubTopic.name + ": " + message);
        if (message == "SUCCESS") {
          // fast-api 로 트래킹이랑 깐부 인식 멈추기 api 하나 만들어서 보내기
          axios.post(state.baseurl + "stop").then((response) => {
            console.log(response);
          });
          router.push("/").catch(() => {});
        }
        state.turtleSubTopic.unsubscribe();
      });
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
