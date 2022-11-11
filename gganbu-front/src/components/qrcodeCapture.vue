<template>
  <div>
    <div id="scanBox">
      <qrcode-stream @decode="onDecode" />
    </div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";
// import { mapActions } from 'vuex'
import $axios from "axios";

export default {
  name: "qrCodeCapture",
  data() {
    return {
      qrcode: String,
      patient: {},
    };
  },
  methods: {
    onDecode(result) {
      // this.$axios.get("http://127.0.0.1:8080/api/patient/" + result).then(function (response) {
      //   console.log("response: ", response);
      //   this.patient = response.data.data;
      //   console.log("patient: ", this.patient);
      // });
      $axios.get("http://127.0.0.1:8080/api/patient/" + result).then((response) => {
        console.log("response: ", response);
        this.patient = response.data.data;
        console.log("patient: ", this.patient);
        this.$store.dispatch("setPatientId", this.patient.patientId);
        this.$store.dispatch("setPatient", this.patient);
      });
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
  transform: rotateY(180deg);
  -webkit-transform: rotateY(180deg);
  /* Safari and Chrome /
  -moz-transform: rotateY(180deg); / Firefox */
}
</style>
