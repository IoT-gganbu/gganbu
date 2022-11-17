<template>
  <div id="app">
    <nav-bar />
    <router-view />
    <custom-modal class="againGuideModal" id="againGuideModal" v-show="showImgModal" @close-modal="showImgModal = false" titleText="">
      <content class="againGuide">
        <div class="text">
          <div class="line">{{ name }}님</div>
          <div class="line">{{ process }}검사가</div>
          <div class="line">완료되었습니다.</div>
          <div class="line">다음 검사실로</div>
          <div class="line">안내하겠습니다.</div>
        </div>
        <custom-button class="yes" btnText="네" @click="toLoading()"></custom-button>
      </content>
    </custom-modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
      springSocketMessage: null,
      showImgModal: false,
      name: "",
      process: "",
    };
  },
  computed: {
    getSpringSocketMessage: function () {
      return this.$store.getters.getSpringSocketMessage;
    },
    getPatient: function () {
      return this.$store.getters.getPatient;
    },
  },
  watch: {
    getSpringSocketMessage(value) {
      console.log("app.vue에서 찍은 socket :", value);
      this.springSocketMessage = value;
      if (this.springSocketMessage.status == 3) {
        this.$router.push("/").catch(() => {});
      } else if (this.springSocketMessage.status == 4) {
        this.$router.push("/").catch(() => {});
        this.showModal();
      }
    },
    getPatient(value) {
      this.process = this.$store.state.progressName[value.task];
      this.name = value.name;
    },
  },
  methods: {
    toLoading() {
      this.showImgModal = false;
      this.$router.push("/loading");
    },
    showModal() {
      this.showImgModal = true;
    },
  },
};
</script>
<style scoped>
.yes {
  font-size: 30px;
  width: 200px;
}
.text {
  margin: 30% 0 10% 0;
  /* font-weight: bold; */
  /* font-size: 30px; */
  font: 34px "Pretendard Bold";
  color: #5780c6;
}
.line {
  margin-bottom: 7px;
}
body {
  margin: 0;
}
#app {
  font-family: "Pretendard";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
}

@font-face {
  font-family: "Pretendard Black";
  src: url("@/assets/font/Pretendard-Black.otf");
}

@font-face {
  font-family: "Pretendard Bold";
  src: url("@/assets/font/Pretendard-Bold.otf");
  font-weight: 700;
}

@font-face {
  font-family: "Pretendard ExtraBold";
  src: url("@/assets/font/Pretendard-ExtraBold.otf");
  font-weight: 900;
}

@font-face {
  font-family: "Pretendard ExtraLight";
  src: url("@/assets/font/Pretendard-ExtraLight.otf");
  font-weight: 100;
}

@font-face {
  font-family: "Pretendard Light";
  src: url("@/assets/font/Pretendard-Light.otf");
  font-weight: 300;
}

@font-face {
  font-family: "Pretendard Medium";
  src: url("@/assets/font/Pretendard-Medium.otf");
  font-weight: 400;
}

@font-face {
  font-family: "Pretendard";
  src: url("@/assets/font/Pretendard-Regular.otf");
  font-weight: 500;
}

@font-face {
  font-family: "Pretendard semiBold";
  src: url("@/assets/font/Pretendard-SemiBold.otf");
  font-weight: 600;
}

@font-face {
  font-family: "Pretendard thin";
  src: url("@/assets/font/Pretendard-Thin.otf");
  font-weight: 200;
}
</style>
