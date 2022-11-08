<template>
  <div>
    <div id="scanBox">
      <qrcode-stream @decode="onDecode" />
    </div>
  </div>
</template>

<script>
// import { createApi } from "@/api";
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  name: "qrCodeCapture",
  data() {
    return {
      qrcode: String,
    };
  },
  methods: {
    onDecode(result) {
      this.$axios.get("http://localhost:8080/api/patient/" + result).then(function (response) {
        console.log(response);
      });
      this.$store.state.patientId = result;
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
