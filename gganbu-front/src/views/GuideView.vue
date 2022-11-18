<template>
  <div class="content1">
    <p class="text">
      {{ name }} 님을 {{ progressName }}검사실로 안내중입니다. <br /><br />
      멈추고 싶으시면 '깐부'라고 불러주세요.
    </p>
    <custom-modal class="modal" id="modal" v-show="showImgModal" @close-modal="showImgModal = false">
      <again-guide class="again"></again-guide>
    </custom-modal>
  </div>
</template>

<script>
import againGuide from "@/components/AgainGuide.vue";
import { mapActions } from "vuex";
export default {
  components: {
    againGuide,
  },
  data() {
    return {
      name: "",
      progressName: "",
      showImgModal: false,
    };
  },
  created() {
    this.name = this.$store.state.patient.name;
    this.progressName = this.$store.state.progressName[this.$store.state.patient.task];
    this.createSubRosTopic();
  },
  computed: {
    getTracking: function () {
      return this.$store.getters.getTracking;
    },
    getVoice: function () {
      return this.$store.getters.getVoice;
    },
  },
  watch: {
    getTracking(value) {
      console.log("tracking : ", value);
      if (!value) {
        this.stopRos();
        this.showImgModal = true;
      }
    },
    getVoice(value) {
      console.log("voice : ", value);
      if (!value) {
        this.stopRos();
        this.showImgModal = true;
      }
    },
  },
  methods: {
    ...mapActions(["createRosTopic", "connectRosSocket", "publishRosSocket"]),
    stopRos() {
      // 1. ros 소켓 연결 확인
      if (this.$store.getters.getRosSocket == null) {
        this.connectRosSocket();
      }
      // 2. topic 생성
      this.createRosTopic(0);
      // 3. topic 메세지 publish
      this.publishRosSocket();
    },
  },
};
</script>

<style scoped>
.content1 {
  height: 730px;
  width: 90%;
  padding-left: 10%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.text {
  text-align: center;
  text-justify: newspaper;
  height: 35%;
  font: 34px "Pretendard ExtraBold";
  color: #5780c6;
}

.modal {
  padding-top: 10%;
}
</style>
