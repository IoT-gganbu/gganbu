<template>
  <div>
    <canvas id="canvas"></canvas>
    <div id="label-container"></div>
  </div>
</template>

<script>
import * as tmPose from "@teachablemachine/pose";

export default {
  data() {
    return {
      model: null,
      webcam: null,
      ctx: null,
      labelContainer: null,
      maxPredictions: 0,
      result: [],
      classPrediction: [],
    };
  },
  mounted() {
    this.init();
  },
  destroyed() {
    this.webcam = null;
  },
  methods: {
    returnData() {
      this.$emit("predictionData", this.classPrediction);
    },
    async init() {
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
      this.webcam.update; // update the webcam frame
      await this.predict();
      window.requestAnimationFrame(this.loop);
    },

    /* 모델과 웹캠에서 받아온 프레임을 비교해서 pose 예측하는 함수 */
    async predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { pose, posenetOutput } = await this.model.estimatePose(this.webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await this.model.predict(posenetOutput);

      for (let i = 0; i < this.maxPredictions; i++) {
        this.classPrediction = prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        // const classPrediction = prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        // this.labelContainer.childNodes[i].innerHTML = classPrediction;
      }

      // finally draw the poses
      this.drawPose(pose);
    },

    /* pose를 영상 위에 그리는 함수 */
    drawPose(pose) {
      if (this.webcam) {
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

<style>
#canvas {
  width: 100%;
  height: 275px;
  object-fit: cover;
  border-radius: 5px;
  border: 2px solid #90b5ff;
}
</style>
