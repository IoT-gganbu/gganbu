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
  created() {
    this.getMemberList();
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
    async getMemberList() {
      await this.$axios.get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`).then((response) => {
        this.$store.commit("SAVE_MEMBER_LIST", response.data.data.content);
      });
    },
    async getPatientList() {
      const patientList = await this.$store.state.memberStore.memberList;
      let set = new Set();

      for (let i = 0; i < patientList.length; i++) {
        let property = new Object();
        property.name = patientList[i].name;
        property.no = patientList[i].residentNo.substr(0, 8) + "******";
        set.add(property);
      }

      this.patientSearchList = set;
    },
    selectedMember(key) {
      this.$store.commit("SET_SELECTED_MEMBER", key);
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
