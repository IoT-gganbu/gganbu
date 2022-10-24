<template>
  <div class="body">
    <div class="left-body">
      <div class="search">
        <div class="search-bar">
          <input placeholder="search" />
        </div>
        <div class="search-btn">
          <button>검색</button>
        </div>
      </div>
      <div class="patient-list">검수자 리스트</div>
    </div>
    <div class="right-body">
      <div class="new-patient">
        <div class="new-patient-btn">
          <button>추가</button>
        </div>
      </div>
      <div class="patient-detail">환자 상세 정보</div>
    </div>
  </div>
</template>
<script>
export default {
  name: "PatientReceiptView",
};
</script>

<style>
.body {
  display: flex;
  flex-direction: row;
}
.left-body {
  display: flex;
  flex-direction: column;
}
.right-body {
  display: flex;
  flex-direction: column;
}
.search {
  display: flex;
  flex-direction: row;
}
.new-patient {
  display: flex;
}
.new-patient-btn {
  margin-left: auto;
}
</style>
