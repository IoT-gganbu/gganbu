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
        <td class="c2">{{ patient.no }}</td>
      </tr>
      <tr>
        <td class="c1">휴대폰 번호</td>
        <td class="c2">{{ patient.phone }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patient: {
        name: "장정1훈",
        age: 28,
        sex: "남",
        no: "950803 - 1xxxxxx",
        phone: "010 - 4328 - 8206",
      },
    };
  },

  method: {
    getPatient() {
      let tmpPatient = this.$store.state.memberStore.memberList.data.content[0];
      this.patient = {
        name: tmpPatient.age,
        age: tmpPatient.residentNo,
        sex: tmpPatient.getPatient,
        no: tmpPatient.residentNo,
        phone: tmpPatient.tel,
      };
    },
  },
  created() {
    console.log("123");
    this.getPatient();
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
</style>
