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
      console.log(this.tracking);
      this.$axios.get(this.$store.state.baseurl + "tracking").then((response) => {
        console.log(response);
        this.tracking = false;
      });
      this.tracking = true;
      console.log(this.tracking);
    },
    startVoice() {
      this.$axios.get(this.$store.state.baseurl + "gganbu").then((response) => {
        console.log(response);
        this.voice = false;
      });
      this.voice = true;
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
  width: 65%;
  padding-left: 18%;
}
</style>
