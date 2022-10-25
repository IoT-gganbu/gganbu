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
        name: "장정훈",
        age: 28,
        sex: "남",
        no: "950803 - 1xxxxxx",
        phone: "010 - 4328 - 8206",
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
  border: 3px solid #90b5ff;
  border-radius: 10px;
  height: 100%;
  margin-top: 10%;
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
  font: 5rem "Pretendard";
  color: #5780c6;
}
td {
  font-size: 2rem;
}
.c1 {
  color: #5780c6;
}
.c2 {
  color: #90b5ff;
}
</style>
