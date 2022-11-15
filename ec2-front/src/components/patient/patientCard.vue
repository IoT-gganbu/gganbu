<template>
  <div class="patient-card">
    <div class="name">{{ patient.name }} 님</div>
    <table class="detail">
      <tr>
        <td class="c1">나이</td>
        <td class="c2">{{ patient.age }} 세</td>
      </tr>
      <tr>
        <td class="c1">성별</td>
        <td class="c2">{{ patient.sex }}</td>
      </tr>
      <tr>
        <td class="c1">주민등록번호</td>
        <td class="c2">{{ patient.no.substr(0, 8) + "******" }}</td>
      </tr>
      <tr>
        <td class="c1">휴대폰 번호</td>
        <td class="c2">{{ patient.phone }}</td>
      </tr>
    </table>
    <custom-button v-if="!patient.isChecked" class="submit" btnText="검사 시작" @click="moveToNextProcess()"></custom-button>
    <custom-button v-else class="proceeding" btnText="검사 중"></custom-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patient: {
        id: "",
        name: "김",
        age: 28,
        sex: "남",
        no: "950803 - 1xxxxxx",
        phone: "010 - 4328 - 8206",
        isChecked: false,
      },
    };
  },
  created() {
    this.getPatientListZero();
  },
  computed: {
    selectMember: function () {
      return this.$store.state.memberStore.selectMember;
    },
    checkedButton: function () {
      return this.isChecked;
    },
    memberList: function () {
      return this.$store.state.memberList;
    },
  },
  watch: {
    selectMember() {
      this.getPatientInfo(this.selectMember);
    },
    checkedButton() {
      this.getPatientInfo(this.selectMember);
    },
    memberList() {
      this.getPatientInfo(0);
    },
  },
  methods: {
    async getPatientListZero() {
      await this.$axios.get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`).then((response) => {
        this.$store.commit("SAVE_MEMBER_LIST", response.data.data.content);
      });
      let tmpPatient = this.$store.state.memberStore.memberList;
      this.patient = {
        id: tmpPatient[0].patientId,
        name: tmpPatient[0].name,
        age: new Date().getFullYear() - Number("19" + tmpPatient[0].residentNo.substr(0, 2)),
        sex: tmpPatient[0].gender == 0 ? "남" : "여",
        no: tmpPatient[0].residentNo,
        phone: tmpPatient[0].tel,
        isChecked: tmpPatient[0].isCheckup,
      };
    },
    getPatientInfo(num) {
      let tmpPatient = this.$store.state.memberStore.memberList;

      this.patient = {
        id: tmpPatient[num].patientId,
        name: tmpPatient[num].name,
        age: new Date().getFullYear() - Number("19" + tmpPatient[num].residentNo.substr(0, 2)),
        sex: tmpPatient[num].gender == 0 ? "남" : "여",
        no: tmpPatient[num].residentNo,
        phone: tmpPatient[num].tel,
        isChecked: tmpPatient[num].isCheckup,
      };
    },
    async moveToNextProcess() {
      await this.$axios.put(`${this.$store.state.baseurl}/staff/receipt`, { residentNo: this.patient.no }).then((response) => {
        console.log(response);
      });
      location.reload();
    },
  },
};
</script>

<style scoped>
.patient-card {
  text-align: center;
  text-decoration: none;
  background-color: #ffffff;
  border: 3px solid #90b5ff;
  border-radius: 10px;
  height: 100%;
  margin: 8% 10% 3% 10%;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.detail {
  text-align: center;
  text-decoration: none;
  justify-content: center;
  margin: 0 10% 0 10%;
}
table {
  width: 80%;
  line-height: 400%;
}
.name {
  font-size: 40px;
  font-weight: bold;
  margin: 4% 0 2% 0;
  color: #5780c6;
}
td {
  font-size: 20px;
}
.c1 {
  color: #5780c6;
}
.c2 {
  color: #90b5ff;
}
.submit,
.proceeding {
  width: 250px;
  margin-bottom: 30px;
}
.proceeding {
  color: #90b5ff;
}
.proceeding:hover {
  background-color: white;
}
</style>
