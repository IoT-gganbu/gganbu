<template>
  <div class="content3">
    <div class="text1">깐부를 불러 멈췄습니다.</div>
    <div class="content2">
      <custom-button btnText="안내 재시작" class="button" @click="again()"></custom-button>
      <custom-button btnText="안내 중지" class="button" @click="exit()"></custom-button>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  methods: {
    ...mapActions(["createRosTopic", "connectRosSocket", "publishRosSocket"]),
    again() {
      // // 1. ros 소켓 연결 확인
      // if (this.$store.getters.getRosSocket == null) {
      //   this.connectRosSocket();
      // }
      // // 2. topic 생성
      // let data = this.$store.getters.getProgressBoolean;
      // this.createRosTopic(data);
      // // 3. topic 메세지 publish
      // this.publishRosSocket();
      this.$router.push("/loading");
    },
    exit() {
      // // 1. ros 소켓 연결 확인
      // if (this.$store.getters.getRosSocket == null) {
      //   this.connectRosSocket();
      // }
      // // 2. topic 생성(로봇 홈으로 보내는 topic)
      // this.createRosTopic(0);
      // // 3. topic 메세지 publish
      // this.publishRosSocket();
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.content2 {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  height: 50%;
  width: 100%;
  padding-top: 30%;
}
.button {
  width: 45%;
  height: 250%;
  font: 28px "Pretendard ExtraBold";
  color: #5780c6;
}
.content3 {
  height: 400px;
}
.text1 {
  padding-top: 20%;
  font: 30px "Pretendard ExtraBold";
  color: #5780c6;
}
</style>
