<template>
  <div>
    <custom-title id="custom_title" titleText="검진자 QR 인식"></custom-title>
    <qrcode-capture />
  </div>
</template>

<script>
import qrcodeCapture from "@/components/qrcodeCapture.vue";
export default {
  components: {
    qrcodeCapture,
  },
};
</script>

<style></style>
