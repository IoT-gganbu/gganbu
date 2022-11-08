<template>
  <div>
    <custom-title titleText="검진자 QR 인식"></custom-title>
    <div class="qrCamera">
      <div class="outline">
        <qrcode-capture />
      </div>
    </div>
    <div class="description">
      <p>
        접수 시 문자로 제공받은<br />
        QR 코드를 화면에 인식시켜주세요.
        {{ qrcodeResult }}
      </p>
    </div>
  </div>
</template>

<script>
import qrcodeCapture from "@/components/qrcodeCapture.vue";
export default {
  components: {
    qrcodeCapture,
  },
  computed: {
    qrcodeResult() {
      return this.$store.state.patientId;
    },
  },
  watch: {
    moveNextPage() {
      if (this.$store.state.patientId != null) {
        this.$router.push("/examination");
      }
    },
  },
};
</script>

<style>
.qrCamera {
  display: flex;
  justify-content: center;
}
.outline {
  width: 80%;
  margin-top: 5%;
  margin-bottom: 8%;
}
.description {
  font-family: "Pretendard Bold";
  color: #5780c6;
  font-size: 25px;
}
</style>
