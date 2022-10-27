<template>
  <div>
    <nav-bar type="접수처" department="소아" professorName="전승준" isLogin="false" />
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

<style>
#custom_title {
  margin-top: 60px;
  margin-left: 60px;
  font-size: 100px;
}
#custom_title > h2 {
  font-size: 30px;
}
#custom_title > h2:after {
  width: 130%;
}
</style>
