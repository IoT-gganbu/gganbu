<template>
  <div>
    <div class="box">
      <div class="boxIn"><p id="text">성함을 말씀해주세요</p></div>
      <button id="btn-read" @click="play()">읽기</button>
      <div>박찬</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
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

    play() {
      const text = document.getElementById("text").innerHTML;
      // console.log(text);
      this.speak(text, {
        rate: 1,
        pitch: 1.2,
        lang: "ko-KR",
      });
    },
  },
};
</script>

<style>
.box {
  width: 80%;
  height: 600px;
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
