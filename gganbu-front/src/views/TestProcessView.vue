<template>
  <div>
    <div class="title">
      <!-- <router-link to="/"><custom-button id="btn" btnText="◀" /></router-link> -->
      <custom-title id="title" titleText="건강검진 절차 안내"></custom-title>
    </div>
    <div class="body">
      <div class="rows" v-for="(data, idx) in processes" :key="idx">
        <custom-button class="col1" :btnText="data.item[0]" @click="showModal(idx, 0)"></custom-button>
        <custom-button class="col2" :btnText="data.item[1]" @click="showModal(idx, 1)"></custom-button>
      </div>
    </div>
    <custom-modal class="detail" id="detail" v-show="showImgModal" @close-modal="showImgModal = false" :titleText="process">
      <content class="content">
        <process-detail :No="processNo" @close-modal="showImgModal = false"></process-detail>
      </content>
    </custom-modal>
  </div>
</template>

<script>
import customTitle from "@/components/common/customTitle.vue";
import processDetail from "@/components/ProcessDetail.vue";
export default {
  components: {
    customTitle,
    processDetail,
  },
  data() {
    return {
      showImgModal: false,
      process: "",
      processNo: 0,
      processes: [
        {
          item: ["1. 접수", "2. 기초검사 / 신체계측"],
        },
        {
          item: ["3. 채혈 / 소변 검사", "4. 흉부 방사선"],
        },
        {
          item: ["5. 진찰 및 상담", "6. 자궁 경부암 검사"],
        },
        {
          item: ["7. 유방암 검사", "8. 위 내시경 검사"],
        },
        {
          item: ["9. 대장 내시경 검사", "10. 폐암 검사"],
        },
      ],
    };
  },
  methods: {
    showModal(idx, no) {
      this.process = this.processes[idx].item[no];
      console.log(this.process);
      this.processNo = idx * 2 + no;
      this.showImgModal = true;
    },
  },
};
</script>

<style scoped>
.title {
  height: 80px;
  /* width: 100%; */
}
#btn {
  margin-top: 3%;
  margin-left: 5%;
  padding: 0.5rem 0.5rem;
  float: left;
}
#title {
  float: left;
  width: 200px;
}
.rows {
  display: flex;
  justify-content: center;
  margin-top: 1.5%;
  height: 20%;
}
.col1,
.col2 {
  width: 50%;
  margin-bottom: 5%;
  margin-left: 5%;
  margin-right: 5%;
  font-size: 1.5rem;
}
.detail {
  height: 100%;
}
</style>
