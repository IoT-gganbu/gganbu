<template>
  <div>
    <div id="scanBox">
      <qrcode-stream @decode="onDecode" />
    </div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  name: "qrCodeCapture",
  data() {
    return {
      qrCodeData: "",
    };
  },

  methods: {
    onDecode(result) {
      this.qrCodeData = result;
    },
  },
  components: {
    QrcodeStream,
  },
};
</script>

<style>
#scanBox {
  width: 380px;
  height: 400px;
  margin: auto;
  display: block;
}
</style>
