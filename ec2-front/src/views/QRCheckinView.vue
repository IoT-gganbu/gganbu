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
    <custom-modal class="nextQr" id="nextQr" v-show="showImgModal" @close-modal="showImgModal = false" :titleText="process">
      <content>
        <div class="patient-info-group">
          <p class="patient-info">성명 : {{ patientName }}</p>
          <p class="patient-info">성별 : {{ patientGender }}</p>
          <p class="patient-info">나이 : {{ patientAge }}</p>
        </div>

        <div class="box" @click="checkStart()" v-if="isChecked == 0">검진 시작</div>
        <div class="box" @click="checkEnd()" v-else-if="isChecked == 1">검진 종료</div>
        <div v-else>
          <p class="already">이미 완료된 검진입니다</p>
          <div class="box2" @click="back()">돌아가기</div>
        </div>
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
      isChecked: 0,
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
          window.alert("처리 완료");
          window.location.reload();
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
          window.alert("처리 완료");
          window.location.reload();
          // pid = 1;
        });
    },

    async getStatus() {
      await this.$axios.get(`${this.$store.state.baseurl}/patient/checkup/${this.$store.state.patientId}/${sessionStorage.getItem("task")}`).then((response) => {
        console.log(response);
        if (response.data.data.status == 3) {
          this.isChecked = 1;
        } else if (response.data.data.status == 4) {
          this.isChecked = 2;
        } else {
          this.isChecked = 0;
        }
      });
    },
    back() {
      this.showImgModal = false;
      window.location.reload();
    },
  },
};
</script>

<style scoped>
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
.patient-info-group {
  margin: 40px 0 0 0;
}
.patient-info {
  margin: 10px;
  color: #5780c6;
  font-size: 40px;
}
.box {
  width: 40%;
  height: 80px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 7% 1% 0 1%;
  font-size: 2em;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  color: #5780c6;
}
.already {
  margin: 20px 0 10px 0;
  font-size: 2em;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  color: red;
}
.box2 {
  width: 40%;
  height: 60px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 1% 1% 0 1%;
  font-size: 1.5em;
  text-align: center;
  text-decoration: none;
  font-weight: bold;
  color: #5780c6;
}
.box:hover {
  background-color: #90b5ff;
}
.nextQr {
  height: 900px;
}
</style>
