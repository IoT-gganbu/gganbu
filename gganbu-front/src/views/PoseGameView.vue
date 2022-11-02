<template>
  <div>
    <custom-button id="backBtn" btnText="돌아가기" />
    <div class="outside-box">
      <div class="box-up">
        <img src="@/assets/img/기초검사.png" />
      </div>
      <div class="box-down">
        <pose-estimation />
      </div>
    </div>
  </div>
</template>

<script>
import poseEstimation from "@/components/poseEstimation.vue";

export default {
  components: { poseEstimation },
  data() {
    return {
      isCorrect: false,
    };
  },
};
</script>

<style>
#backBtn {
  position: absolute;
  margin-top: 10px;
}
.outside-box {
  margin-top: 8%;
  width: 75%;
  height: 600px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  padding: 30px;
  text-align: center;
  margin: 9% 3% 0 3%;
}
.box-up {
}
img {
  width: 100%;
  object-fit: cover;
}
</style>
