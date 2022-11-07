<template>
  <div>
    <div class="title">
      <!-- <router-link to="/"><custom-button id="btn" btnText="◀" /></router-link> -->
      <custom-title titleText="건강검진 진척도"></custom-title>
    </div>
    <div class="body">
      <div class="row" v-for="(data, idx) in processes" :key="idx">
        <div class="col1">
          <div class="boxIn">
            <img :src="data.image[0]" class="img" />
            <div class="titleBox" v-text="data.item[0]"></div>
            <br :key="idx" />
          </div>
        </div>
        <!-- <img src="@/assets/img/examination/arrow_down.png" /> -->
        <div class="col2">
          <div class="boxIn">
            <img :src="data.image[1]" class="img" />
            <div class="titleBox" v-text="data.item[1]"></div>
          </div>
        </div>
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
// import arrowDown from "@/assets/img/examination/arrow_down.png";
// import arrowUp from "@/assets/img/examination/arrow_up";
// import arrowRight from "@/assets/img/examination/arrow_right.png";

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
          item: [`접수 및 문진표 작성`, "폐암 검사"],
          image: [require("@/assets/img/examination/1.png"), require("@/assets/img/examination/2.png")],
        },
        {
          item: ["기초 검사 / 신체 계측", "대장암 검사"],
          image: [require("@/assets/img/examination/3.png"), require("@/assets/img/examination/4.png")],
        },
        {
          item: ["채혈 / 소변 검사", "위암 검사"],
          image: [require("@/assets/img/examination/5.png"), require("@/assets/img/examination/6.png")],
        },
        {
          item: ["흉부 방사선", "유방암 검사"],
          image: [require("@/assets/img/examination/7.png"), require("@/assets/img/examination/8.png")],
        },
        {
          item: ["진찰 및 상담", "자궁경부암 검사"],
          image: [require("@/assets/img/examination/9.png"), require("@/assets/img/examination/10.png")],
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
  height: 50px;
}
.titleBox {
  height: 70px;
  color: #5780c6;
  font-weight: bold;
  margin-top: 15px;
  margin-left: 10px;
  align-items: center;
}
.row {
  display: flex;
  justify-content: center;
  margin-top: 1.5%;
  height: 20%;
}
.col1,
.col2 {
  /* height: 0px; */
  width: 40%;
  font-size: 1.3rem;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 3% 1% 0 1%;
}
.img {
  height: 60px;
  width: 60px;
  margin-top: 10px;
}
.detail {
  height: 120%;
}
.choiceBox:hover {
  background-color: #90b5ff;
}
.boxIn {
  display: flex;
  text-align: center;
  margin: 5px;
}
</style>
