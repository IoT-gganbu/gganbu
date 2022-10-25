<template>
  <div class="body">
    <div class="left-body">
      <div class="search">
        <div class="search-bar">
          <input placeholder="Search" class="input" />
        </div>
        <div class="search-btn">
          <custom-button btnText="검색"></custom-button>
        </div>
      </div>
      <!-- <div class="patient-list">검수자 리스트</div> -->
      <patient-list></patient-list>
    </div>
    <div class="right-body">
      <div class="new-patient">
        <div class="new-patient-btn">
          <custom-button btnText="추가"></custom-button>
        </div>
      </div>
      <patient-card></patient-card>
    </div>
  </div>
</template>
<script>
export default {
  name: "PatientReceiptView",
};
</script>

<style scoped>
.body {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: center;
  height: 100%;
}
.left-body {
  display: flex;
  flex-direction: column;
  width: 40%;
  margin-right: 5%;
}
.right-body {
  display: flex;
  flex-direction: column;
  width: 40%;
}
.input {
  background: #ffffff;
  border: 1px solid #90b5ff;
  border-radius: 3px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 70%;
  height: 90%;
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
  height: 9%;
  width: 100%;
}
.new-patient {
  display: flex;
}
.new-patient-btn {
  margin-left: 85%;
}
</style>
