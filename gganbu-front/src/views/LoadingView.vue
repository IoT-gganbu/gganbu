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
    return {
      tracking: false,
      voice: false,
    };
  },
  created() {
    this.connectSpringSocket();
  },
  mounted() {
    // 트래킹이랑 깐부 인식을 언제 켜야하지
    // if (this.$store.state.turtleBotState == 0) {
    setTimeout(() => {
      this.startTracking();
      this.startVoice();
    }, 3000);
    setTimeout(() => {
      if (this.checkConnection()) {
        this.$router.push("examination");
      }
    }, 6000);
    // }
  },
  methods: {
    ...mapActions(["connectSpringSocket"]),
    startTracking() {
      console.log(this.tracking + "트래킹 시작 전입니다");
      this.$store.state.tracking = true;
      this.$axios.get(this.$store.state.baseurl + "tracking").then((response) => {
        console.log(response);
        this.tracking = false;
        this.$store.state.tracking = false;
      });
      this.tracking = true;
      console.log(this.tracking + "트래킹 시작 후입니다");
    },
    startVoice() {
      console.log(this.tracking + "깐부 시작 전입니다");
      this.$store.state.voice = true;
      this.$axios.get(this.$store.state.baseurl + "gganbu").then((response) => {
        console.log(response);
        this.voice = false;
        this.$store.state.voice = false;
      });
      this.voice = true;
      console.log(this.tracking + "깐부 시작 후입니다");
    },
    checkConnection() {
      if (this.tracking && this.voice) {
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
  width: 83%;
  padding-left: 20%;
}
</style>
