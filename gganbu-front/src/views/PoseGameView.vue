<template>
  <div class="posegameview">
    <div class="top">
      <custom-title id="title" titleText="스트레칭" />
    </div>
    <div class="game-box">
      <div class="result-container">정확도: {{ score }}</div>
      <div class="box-up">
        <img :src="images[imageIndex]" />
      </div>
      <div class="box-down">
        <canvas id="canvas"></canvas>
        <div id="label-container"></div>
      </div>
    </div>
    <custom-modal class="game-end" v-show="showModal" @close-modal="showModal = false" :titleText="endTitle">
      <content class="content">
        <p>축하합니다!<br />총 {{ time }} 초 걸리셨습니다.</p>
      </content>
      <div class="btnClass">
        <router-link to="/">
          <custom-button class="homeBtn" btnText="홈으로" />
        </router-link>
        <router-link to="/gameChoice">
          <custom-button class="retryBtn" btnText="다시하기" />
        </router-link>
      </div>
    </custom-modal>
  </div>
</template>

<script>
import * as tmPose from "@teachablemachine/pose";
import handSide from "@/assets/model/model_img/hand_side.png";
import handUp from "@/assets/model/model_img/hand_up.png";
import neckSideL from "@/assets/model/model_img/neck_side_left.png";
import neckSideR from "@/assets/model/model_img/neck_side_right.png";

export default {
  data() {
    return {
      images: ["", handUp, handSide, neckSideL, neckSideR],
      imageIndex: 1,
      endTitle: "게임이 종료되었습니다.",
      score: "게임 시작 중",
      corrects: 0,
      showModal: false,
      time: 0,
      content: "",
      interval: undefined,

      model: null,
      webcam: null,
      ctx: null,
      labelContainer: null,
      maxPredictions: 0,
      classPrediction: [],
    };
  },
  mounted() {
    console.log("pose game start");
    this.interval = setInterval(this.startGame, 1000);
    this.time = new Date().getTime();
    this.init();
  },
  beforeDestroy() {
    console.log("pose game end");
    clearInterval(this.startGame);
    this.webcam.stop();
  },
  methods: {
    showEndModal() {
      this.showModal = true;
    },
    startGame() {
      this.score = this.classPrediction[this.imageIndex] * 100;
      if (this.imageIndex == 5) {
        this.showEndModal();
        this.time = Math.ceil((new Date().getTime() - this.time) / 1000);
        clearInterval(this.interval); // 프로그램 종료
      } else if (this.corrects >= 5) {
        this.imageIndex++;
        this.corrects = 0;
      } else if (this.score >= 70) {
        this.corrects++;
      }

      console.log(this.classPrediction);
      console.log(this.score);
      console.log(this.corrects);
    },
    async init() {
      // const URL = "../assets/model/";
      const URL = "https://teachablemachine.withgoogle.com/models/k1fCLdzxV/";
      const modelURL = URL + "model.json";
      const metadataURL = URL + "metadata.json";

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // Note: the pose library adds a tmPose object to your window (window.tmPose)
      this.model = await tmPose.load(modelURL, metadataURL);
      this.maxPredictions = this.model.getTotalClasses();

      // Convenience function to setup a webcam
      const sizeW = 412; // 결과창 width
      const sizeH = 275; // 결과창 height
      const flip = true; // 카메라 뒤집기
      this.webcam = new tmPose.Webcam(sizeW, sizeH, flip);
      await this.webcam.setup(); // request access to the webcam
      await this.webcam.play();
      window.requestAnimationFrame(this.loop);

      // append/get elements to the DOM
      const canvas = document.getElementById("canvas");
      canvas.width = sizeW;
      canvas.height = sizeH;
      this.ctx = canvas.getContext("2d");
      this.labelContainer = document.getElementById("label-container");

      for (let i = 0; i < this.maxPredictions; i++) {
        // and class labels
        this.labelContainer.appendChild(document.createElement("div"));
      }
    },

    /* 루프 돌면서 pose estimation 하는 함수 */
    async loop() {
      this.webcam.update(); // update the webcam frame
      await this.predict();

      window.requestAnimationFrame(this.loop); // 프레임 최신화
    },

    /* 모델과 웹캠에서 받아온 프레임을 비교해서 pose 예측하는 함수 */
    async predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { pose, posenetOutput } = await this.model.estimatePose(this.webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await this.model.predict(posenetOutput);

      for (let i = 0; i < this.maxPredictions; i++) {
        // this.classPrediction[i] = prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        // const classPrediction = prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        // this.labelContainer.childNodes[i].innerHTML = classPrediction;
        this.classPrediction[i] = prediction[i].probability.toFixed(2);
        // console.log(this.classPrediction);
      }

      // finally draw the poses
      this.drawPose(pose);
    },

    /* pose를 영상 위에 그리는 함수 */
    drawPose(pose) {
      if (this.webcam.canvas) {
        this.ctx.drawImage(this.webcam.canvas, 0, 0);
        // draw the keypoints and skeleton
        if (pose) {
          const minPartConfidence = 0.5;
          tmPose.drawKeypoints(pose.keypoints, minPartConfidence, this.ctx);
          tmPose.drawSkeleton(pose.keypoints, minPartConfidence, this.ctx);
        }
      }
    },
  },
};
</script>

<style scoped>
#title {
  width: 100px;
  float: left;
  margin-left: 30px;
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
.content {
  font-size: 40px;
  color: #5780c6;
}
.result-container {
  height: 35px;
  float: left;
  text-align: left;
  font-size: 30px;
  color: #5780c6;
}
#canvas {
  width: 100%;
  height: 275px;
  object-fit: cover;
  border-radius: 5px;
  border: 2px solid #90b5ff;
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
.homeBtn,
.retryBtn {
}
.btnClass {
  margin-top: 200px;
}
</style>
