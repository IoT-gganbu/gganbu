<template>
  <div class="posegameview">
    <div class="top">
      <custom-title id="title" titleText="스트레칭" />
      <custom-button id="backBtn" btnText="< 돌아가기" />
    </div>
    <div class="game-box">
      <div class="result-container">정확도: {{ text }}</div>
      <div class="box-up">
        <img :src="images[imageIndex]" />
      </div>
      <div class="box-down">
        <pose-estimation @predictionData="showData" />
      </div>
    </div>
  </div>
</template>

<script>
import poseEstimation from "@/components/poseEstimation.vue";
import handSide from "@/assets/model/model_img/hands_side_sj.png";
import handUp from "@/assets/model/model_img/hands_up_sj.png";
import neckSideL from "@/assets/model/model_img/neck_side_left_sj.png";
import neckSideR from "@/assets/model/model_img/neck_side_right_sj.png";

export default {
  components: { poseEstimation },
  data() {
    return {
      isCorrect: false,
      images: [handSide, handUp, neckSideL, neckSideR],
      imageIndex: 0,
      text: "",
      resultContainer: null,
    };
  },
  mounted() {
    // setInterval(this.startGame, 2000);
    // setInterval(this.showData, 2000);
    this.startGame();
    this.showData();
  },
  methods: {
    showData(data) {
      this.resultContainer = document.getElementById("result-container");
      const prediction = data;

      for (let i = 0; i < this.maxPredictions; i++) {
        this.resultContainer.childNodes[i].innerHTML = prediction;
      }

      console.log(JSON.stringify(data), "data sent");
    },
    startGame() {
      this.imageIndex = (this.imageIndex + 1) % this.images.length;
    },
  },
  beforeDestroy() {
    console.log("1231321");
    clearInterval(this.startGame);
    clearInterval(this.showData);
  },
};
</script>

<style>
#title {
  width: 100px;
  float: right;
  margin-right: 50px;
}
#backBtn {
  float: left;
  margin-top: 10px;
  margin-left: 30px;
}
.top {
  float: inherit;
}
.posegameview {
  margin-top: 10px;
  text-align: center;
}
.result-container {
  height: 30px;
  float: left;
  text-align: left;
  font-size: 30px;
  color: #5780c6;
}
.game-box {
  margin-top: 5%;
  width: 85%;
  height: 580px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  padding: 10px;
  text-align: center;
}
.box-up {
  height: 275px;
  width: 100%;
}
.box-up > img {
  width: 100%;
  height: 275px;
  object-fit: scale-down;
  border-radius: 5px;
}
.box-down > * {
  border-radius: 5px;
}
</style>
