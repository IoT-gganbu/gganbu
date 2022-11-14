<template>
  <div class="body">
    <div class="left-body">
      <div class="search">
        <div class="search-bar">
          <input placeholder="Search" class="input" v-model="searchName" />
        </div>
        <div class="search-btn">
          <custom-button btnText="검색" @click="searchPatient(searchName)"></custom-button>
        </div>
      </div>
      <patient-list></patient-list>
    </div>
    <div class="right-body">
      <div class="new-patient">
        <div class="new-patient-btn">
          <custom-button btnText="추가" @click="showImgModal = true"></custom-button>
        </div>
      </div>
      <patient-card></patient-card>
    </div>
    <custom-modal class="addPatient" id="addPatient" v-show="showImgModal" @close-modal="showImgModal = false" titleText="환자 등록">
      <content>
        <add-patient></add-patient>
      </content>
    </custom-modal>
  </div>
</template>
<script>
import AddPatient from "@/components/patient/addPatient.vue";
import PatientCard from "@/components/patient/patientCard.vue";

export default {
  name: "PatientReceiptView",
  components: {
    AddPatient,
    PatientCard,
  },
  data() {
    return {
      showImgModal: false,
      searchName: "",
    };
  },
  mounted() {
    // this.getPatientList();
  },
  methods: {
    async getAllPatientList() {
      await this.$axios.get(`${this.$store.state.baseurl}/patient/searchAllPatients?size=10`).then((response) => {
        this.$store.commit("SAVE_MEMBER_LIST", response.data);
      });
    },
    async searchPatient(searchName) {
      await this.$axios
        .get(`${this.$store.state.baseurl}/patient/search/${searchName}`)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
input:focus {
  outline: 2px solid #90b5ff;
}
.body {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: center;
  height: 100%;
  margin-top: 5%;
}
.left-body {
  display: flex;
  flex-direction: column;
  width: 40%;
  text-align: center;
}
.right-body {
  display: flex;
  flex-direction: column;
  width: 40%;
}
.input {
  background: #ffffff;
  border: 2px solid #90b5ff;
  border-radius: 3px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 80%;
  height: 95%;
}
input::-webkit-input-placeholder {
  background-image: url(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnRdQ3uLA3uuoM-wXkg-x8fFh_RWxwoWT5KDI745hSV4ldIdm3nus-HDaIXy23oBXemfc&usqp=CAU);
  background-size: 4%;
  background-position: 1px center;
  background-repeat: no-repeat;
  text-indent: 0;
  padding-left: 5%;
  background-color: #ffffff;
}
.search-btn {
  margin-bottom: auto;
  margin-top: auto;
}
.search-bar {
  width: 70%;
  height: 100%;
}
.search {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-content: center;
  height: 7%;
  width: 100%;
}
.new-patient {
  display: flex;
  height: 9%;
  width: 100%;
}
.new-patient-btn {
  margin-left: 80%;
}
.addPatient {
  height: 130%;
}
</style>
