<template>
  <div>
    <div class="box">
      <div class="boxIn">
        <p id="text" :value="[question, cnt]">{{ question[cnt] }}</p>
        <p id="finish" v-if="isFinished">문진이 완료되었습니다</p>
      </div>
      <div v-if="!isLoading"></div>
      <spinner-view v-if="isLoading"></spinner-view>
      <router-link :to="{ name: 'loading' }" class="link"><custom-button btnText="안내시작" v-if="isFinished"></custom-button></router-link>
      <!-- <div :value="res">{{ res }}</div> -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      question: [
        "인플루엔자 예방접종을 매년 하십니까?",
        "지금까지 다섯갑 이상의 담배를 피운 적이 있습니까?",
        "한 달에 몇번 세잔 이상의 음주를 하십니까?",
        "부모, 형제, 자매 중에 당뇨를 앓은 경우가 있습니까?",
      ],
      res: [],
      cnt: 0,
      isLoading: false,
      isFinished: false,
    };
  },

  mounted() {
    this.play();
  },

  watch: {
    cnt() {
      setTimeout(() => {
        this.play();
      }, 1000);
    },
  },

  methods: {
    speak(text, opt_prop) {
      if (typeof SpeechSynthesisUtterance === "undefined" || typeof window.speechSynthesis === "undefined") {
        alert("이 브라우저는 음성 합성을 지원하지 않습니다.");
        return;
      }

      window.speechSynthesis.cancel(); // 현재 읽고있다면 초기화

      const prop = opt_prop || {};

      const speechMsg = new SpeechSynthesisUtterance();
      speechMsg.rate = prop.rate || 1; // 속도: 0.1 ~ 10
      speechMsg.pitch = prop.pitch || 1; // 음높이: 0 ~ 2
      speechMsg.lang = prop.lang || "ko-KR";
      speechMsg.text = text;

      // SpeechSynthesisUtterance에 저장된 내용을 바탕으로 음성합성 실행
      window.speechSynthesis.speak(speechMsg);
    },

    async play() {
      const text = document.getElementById("text").innerHTML;

      console.log(text);
      await this.speak(text, {
        rate: 1,
        pitch: 1.2,
        lang: "ko-KR",
      });
      setTimeout(() => {
        this.getData();
      }, 3500);
    },
    async getData() {
      if (this.question.length == this.cnt) {
        console.log("finish");
        this.isFinished = true;
        this.sendMail();
        return "finish";
      }
      this.isLoading = true;
      await this.$axios.get(this.$store.state.baseurl + "record/save").then((response) => {
        this.isLoading = false;
        this.res.push(response.data);
        console.log(response.data);
        this.cnt += 1;
      });
    },

    sendMail() {
      const headers = {
        "Content-Type": "application/json",
      };
      console.log(this.res);
      this.$axios.post(this.$store.state.baseurl + "email", { data: this.res }, { headers: headers }).then((response) => {
        console.log(response);
        this.$store.commit("changeChecked");
      });
    },

    // saveData(request) {
    //   this.res = request;
    // },
  },
};
</script>

<style>
.box {
  width: 80%;
  height: 650px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
  font-size: 2em;
}
</style>
