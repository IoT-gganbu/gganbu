<template>
  <div class="content1">
    <div class="question">
      <p class="text">다음 장소로 안내를 시작하고 싶으시면 안내시작 버튼을 눌러주세요</p>
    </div>
    <custom-button btnText="안내 시작" class="button" @click="startGuide()"></custom-button>
  </div>
</template>

<script>
export default {
  methods: {
    startGuide() {
      this.$router.push("/guide");
    },
  },
};
</script>

<style scoped>
.content1 {
  height: 100%;
  width: 80%;
  display: flex;
  flex-direction: column;
  padding-left: 20%;
}
.question {
  width: 100%;
  height: 100%;
  display: flex;
}
.text {
  padding-top: 20%;
  width: 120%;
  font: 20px "Pretendard ExtraBold";
  color: #5780c6;
}
.button {
  width: 100%;
  height: 150%;
  font: 36px "Pretendard ExtraBold";
  color: #5780c6;
}
</style>
