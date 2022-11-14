<template>
  <div>
    <custom-title id="custom_title" titleText="검진자 QR 인식"></custom-title>
    <button @click="getStatus()">adsad</button>
    <div id="scanBox">
      <qrcode-stream @decode="onDecode" />
    </div>
    <div class="description">
      <p>
        검수자의 QR 코드를<br />
        화면에 인식시켜주세요.
      </p>
    </div>
    <custom-modal class="nextQr" id="nextQr" v-show="showImgModal" @close-modal="showImgModal = false" titleText="">
      <content>
        <p class="patient-info">{{ patientName }} / {{ patientGender }} / {{ patientAge }}</p>
        <p class="patient-info">{{ process }}</p>
        <div class="box" @click="checkStart()" v-if="isChecked">검진 시작</div>
        <div class="box" @click="checkEnd()" v-else>검진 종료</div>
      </content>
    </custom-modal>
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
      showImgModal: false,
      patientName: "",
      patientGender: "",
      patientAge: 0,
      entireProcess: [],
      process: "",
      isChecked: true,
    };
  },

  methods: {
    async onDecode(result) {
      this.$store.state.patientId = result;
      this.getStatus();
      console.log(this.isChecked);
      this.showImgModal = true;
      // let pid = 0;
      // await this.$axios.get(`${this.$store.state.baseurl}/staff/progress/${result}`).then((response) => {
      //   console.log(response);
      //   // pid = 1;
      // });
      // await this.$axios
      //   .post(`${this.$store.state.baseurl}patient/checkup/${pid}`, {
      //     patientId: pid,
      //     tcId: 2,
      //   })
      //   .then((response) => {
      //     console.log(response);
      //   })
      //   .catch((error) => {
      //     console.log(error.response);
      //   });
      // this.$store.state.patientId = result;

      await this.$axios.get(`${this.$store.state.baseurl}/patient/${this.$store.state.patientId}`).then((response) => {
        console.log(response.data);
        this.patientName = response.data.data.name;
        this.patientGender = response.data.data.gender == 0 ? "남" : "여";
        this.patientAge = response.data.data.age;
        this.entireProcess = response.data.data.task;
      });

      const task = sessionStorage.getItem("task");

      for (let i = 0; i < this.entireProcess.length; i++) {
        if (task == this.entireProcess[i].tcId) {
          console.log("aa");
          this.process = this.entireProcess[i].checkTitle;
        }
      }
    },

    async checkStart() {
      const headers = {
        "Content-Type": "application/json",
      };

      await this.$axios
        .put(`${this.$store.state.baseurl}/patient/checkup`, { patientId: this.$store.state.patientId, tcId: sessionStorage.getItem("task"), status: 3 }, { headers: headers })
        .then((response) => {
          console.log(response);
          this.showImgModal = false;
          // pid = 1;
        });
    },

    async checkEnd() {
      const headers = {
        "Content-Type": "application/json",
      };
      await this.$axios
        .put(`${this.$store.state.baseurl}/patient/checkup`, { patientId: this.$store.state.patientId, tcId: sessionStorage.getItem("task"), status: 4 }, { headers: headers })
        .then((response) => {
          console.log(response);
          this.showImgModal = false;
          // pid = 1;
        });
    },

    async getStatus() {
      await this.$axios.get(`${this.$store.state.baseurl}/patient/checkup/${this.$store.state.patientId}/${sessionStorage.getItem("task")}`).then((response) => {
        console.log(response);
        if (response.data.data.status == 3) {
          this.isChecked = false;
        } else {
          this.isChecked = true;
        }
      });
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
.patient-info {
  color: #5780c6;
  font-size: 30px;
}
.box {
  width: 40%;
  height: 100px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
  font-size: 2em;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  color: #5780c6;
}
.box:hover {
  background-color: #90b5ff;
}
</style>
