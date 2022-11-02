<template>
  <div>
    <div class="box">
      <pose-estimation />
      <custom-button btnText="123123" />
    </div>
  </div>
</template>

<script>
import poseEstimation from "@/components/poseEstimation.vue";

export default {
  components: { poseEstimation },
};
</script>

<style>
.box {
  width: 85%;
  height: 600px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  /* margin: 9% 1% 0 1%; */
}
</style>
