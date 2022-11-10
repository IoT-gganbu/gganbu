<template>
  <div>
    <custom-title id="custom_title" titleText="검진자 QR 인식"></custom-title>
    <div id="scanBox">
      <qrcode-stream @decode="onDecode" />
    </div>
    <div class="description">
      <p>
        검수자의 QR 코드를<br />
        화면에 인식시켜주세요.
      </p>
    </div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  name: "qrCheckinView",
  components: {
    QrcodeStream,
  },
  data() {
    return {
      qrCodeData: "",
    };
  },

  methods: {
    async onDecode(result) {
      let pid = 0;
      // await this.$axios.get(this.$store.state.baseurl + "staff/progress/" + result).then(function (response) {
      //   console.log(response);
      pid = 1;
      // });
      await this.$axios
        .post(`${this.$store.state.baseurl}patient/checkup/${pid}`, {
          patientId: pid,
          tcId: 2,
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error.response);
        });
      this.$store.state.patientId = result;
      await this.$axios.get;
    },
  },
};
</script>

<style>
#scanBox {
  width: 800px;
  height: 480px;
  margin: auto;
  display: block;
}
.description {
  font-family: "Pretendard Bold";
  color: #5780c6;
  font-size: 25px;
}
</style>
