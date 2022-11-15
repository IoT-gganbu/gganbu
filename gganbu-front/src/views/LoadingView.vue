<template>
  <div class="body1">
    <div class="spinner">
      <loading-spinner size="massive" message="사용자를 인식중입니다. 한걸음 물러나 주세요!"></loading-spinner>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {};
  },
  created() {
    // 소켓 연결이 되어있다면 다시 연결하지 않게 예외처리
    // if (!this.$store.state.springSocketConnected) {
    this.connectSpringSocket();
    // }
    // this.nextProgress();
  },
  mounted() {
    setTimeout(() => {
      this.startTracking();
      this.startVoice();
    }, 3000);
    setTimeout(() => {
      if (this.checkConnection()) {
        this.$router.push("examination");
      }
    }, 6000);
  },
  methods: {
    ...mapActions(["connectSpringSocket"]),
    startTracking() {
      this.$store.commit("changeTracking");
      console.log(1);
      this.$axios.get(this.$store.state.baseurl + "tracking").then((response) => {
        console.log(response);
        console.log(2);
        this.$store.commit("changeTracking");
        this.tracking = false;
      });
      console.log(3);
    },
    startVoice() {
      this.$store.commit("changeVoice");
      this.$axios.get(this.$store.state.baseurl + "gganbu").then((response) => {
        console.log(response);
        this.$store.commit("changeVoice");
      });
    },
    checkConnection() {
      if (this.$store.state.tracking && this.$store.state.voice) {
        return true;
      } else return false;
    },
  },
};
</script>

<style>
.body1 {
  display: flex;
  height: 100%;
  width: 100%;
  align-content: center;
  justify-content: center;
  padding-top: 50%;
}
.vue-simple-spinner-text {
  width: 80%;
  padding-left: 18%;
}
</style>
