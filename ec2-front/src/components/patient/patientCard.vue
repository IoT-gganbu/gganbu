<template>
  <div class="patient-card">
    <div class="name">{{ patient.name }} 님</div>
    <table class="detail">
      <tr>
        <td>나이</td>
        <td>{{ patient.age }} 세</td>
      </tr>
      <tr>
        <td>성별</td>
        <td>{{ patient.sex }}</td>
      </tr>
      <tr>
        <td>주민등록번호</td>
        <td>{{ patient.no }}</td>
      </tr>
      <tr>
        <td>휴대폰 번호</td>
        <td>{{ patient.phone }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patient: {
        name: "장정훈",
        age: 28,
        sex: "남",
        no: "950803-1xxxxxx",
        phone: "010-4328-8206",
      },
    };
  },
};
</script>

<style scoped>
.patient-card {
  text-align: center;
  text-decoration: none;
  background-color: #ffffff;
  border: 2px solid #90b5ff;
  border-radius: 10px;
  height: 100%;
}
.detail {
  text-align: center;
  text-decoration: none;
}
table {
  width: 100%;
  text-align: center;
  text-decoration: none;
  line-height: 500%;
}
.name {
  font-size: 5rem;
}
td {
  font-size: 1.5rem;
}
</style>
