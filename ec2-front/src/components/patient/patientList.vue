<template>
  <div class="patient-list" v-if="patientSearchList">
    <table>
      <thead class="head">
        <th class="name">성명</th>
        <th class="no">주민등록번호</th>
      </thead>
      <tbody class="body">
        <tr v-for="(line, key) in patientSearchList" @click="selectedMember(key)" v-bind:key="key" class="body-row">
          <td>{{ line.name }}</td>
          <td>{{ line.no }}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div v-else class="noResult">검색 결과가 없습니다.</div>
</template>

<script>
export default {
  data() {
    return {
      patientSearchList: [
        { name: "장정훈", no: "950803 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
        { name: "이한기", no: "951111 - 1xxxxxx" },
      ],
    };
  },
<<<<<<< HEAD
  mounted() {
    this.getAllPatientsList();
    console.log(this.$store.state.memberStore.memberList);
  },
  methods: {
    async getAllPatientsList() {
      await this.$axios
        .get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`)
        .then((response) => {
          this.$store.commit("GET_MEMBER_LIST_ALL", response.data.data.content);
        })
        .catch((error) => {
          console.log(error);
        });
=======
  created() {
    this.getPatientList();
  },
  computed: {
    memberList: function () {
      return this.$store.state.memberStore.memberList;
    },
  },
  watch: {
    memberList() {
      this.getPatientList();
    },
  },
  methods: {
    async getPatientList() {
      const patientList = await this.$store.state.memberStore.memberList;
      console.log(patientList);
      for (let i = 0; i < 9; i++) {
        this.patientSearchList[i].name = patientList.data.content[i].name;
        this.patientSearchList[i].no = patientList.data.content[i].residentNo;
      }

      // await this.$axios.get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`).then((response) => {
      //   this.$store.commit("SAVE_MEMBER_LIST", response.data);

      //   for (let i = 0; i < 9; i++) {
      //     this.patientSearchList[i].name = this.$store.state.memberStore.memberList.data.content[i].name;
      //     this.patientSearchList[i].no = this.$store.state.memberStore.memberList.data.content[i].residentNo;
      //   }
      // });
    },
    selectedMember(key) {
      this.$store.commit("SET_SELECTED_MEMBER", key);
>>>>>>> bbfba43ba4c042171bb58202c2af7f4e68f93ae2
    },
  },
};
</script>

<style scoped>
.patient-list {
  background-color: #ffffff;
  border-radius: 10px;
  text-align: center;
  display: flex;
  justify-content: center;
  width: 100%;
  margin-top: 8%;
}
.name {
  width: 30%;
}
.no {
  width: 100%;
}
table {
  width: 100%;
  border-collapse: collapse;
}
.head {
  color: #5780c6;
  height: 40px;
}
.body {
  color: #667080;
}
td {
  border-bottom: 0.5px solid #919aa9;
}
th {
  background-color: #c1d6ff;
  border-bottom: 2px solid #919aa9;
}
tr {
  height: 40px;
}
.noResult {
  text-align: center;
  width: 100%;
  font-size: 2rem;
  margin-top: 20%;
}
.body-row:hover {
  background: #90b5ff;
}
</style>
