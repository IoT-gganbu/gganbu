<template>
  <div>
    <div class="title">
      <!-- <router-link to="/"><custom-button id="btn" btnText="◀" /></router-link> -->
      <custom-title id="titleText" titleText=" 님의 건강검진 진척도" :name="name"></custom-title>
    </div>
    <div class="body">
      <div class="row" v-for="(data, idx) in processes" :key="idx">
        <div class="col1" v-if="data.item[0] != ''">
          <div class="boxIn">
            <img :src="data.image[0]" class="img" />
            <div class="titleBox" v-html="data.item[0]"></div>
            <!-- <br :key="idx" /> -->
          </div>
        </div>
        <div class="col2" v-if="data.item[1] != ''">
          <div class="boxIn">
            <img :src="data.image[1]" class="img" />
            <div class="titleBox" v-html="data.item[1]"></div>
          </div>
        </div>
        <div class="arrow1" v-if="data.item[0] == ''">
          <img :src="data.image[0]" class="arrow_img" />
        </div>
        <div class="arrow2" v-if="data.item[1] == ''">
          <img :src="data.image[1]" class="arrow_img" />
          <br :key="idx" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customTitle from "@/components/common/customTitle.vue";
// import processDetail from "@/components/ProcessDetail.vue";
// import arrowDown from "@/assets/img/examination/arrow_down.png";
// import arrowUp from "@/assets/img/examination/arrow_up";
// import arrowRight from "@/assets/img/examination/arrow_right.png";

export default {
  components: {
    customTitle,
  },
  data() {
    return {
      showImgModal: false,
      name: "전승준",
      process: "",
      processNo: 0,
      processes: [
        {
          item: [`접수 및 <br />문진표 작성`, "폐암 검사"],
          image: [require("@/assets/img/examination/1.png"), require("@/assets/img/examination/2.png")],
        },
        {
          item: ["", ""],
          image: [require("@/assets/img/examination/arrow_down.png"), require("@/assets/img/examination/arrow_up.png")],
        },
        {
          item: ["기초 검사 / 신체 계측", "대장암<br>검사"],
          image: [require("@/assets/img/examination/3.png"), require("@/assets/img/examination/4.png")],
        },
        {
          item: ["", ""],
          image: [require("@/assets/img/examination/arrow_down.png"), require("@/assets/img/examination/arrow_up.png")],
        },
        {
          item: ["채혈 / 소변 검사", "위암 검사"],
          image: [require("@/assets/img/examination/5.png"), require("@/assets/img/examination/6.png")],
        },
        {
          item: ["", ""],
          image: [require("@/assets/img/examination/arrow_down.png"), require("@/assets/img/examination/arrow_up.png")],
        },
        {
          item: ["흉부<br>방사선", "유방암<br>검사"],
          image: [require("@/assets/img/examination/7.png"), require("@/assets/img/examination/8.png")],
        },
        {
          item: ["", ""],
          image: [require("@/assets/img/examination/arrow_down.png"), require("@/assets/img/examination/arrow_up.png")],
        },
        {
          item: ["진찰 및<br>상담", "자궁경부암 검사"],
          image: [require("@/assets/img/examination/9.png"), require("@/assets/img/examination/10.png")],
        },
      ],
    };
  },
  mounted() {
    this.currentProgress(0);
  },
  methods: {
    currentProgress(idx) {
      document.querySelector(this.process[idx]);
    },
  },
  created() {
    if (this.$store.state.patientId != null) {
      console.log("12");
    }
  },
};
</script>

<style scoped>
.title {
  height: 50px;
}
#titleText {
  float: left;
  width: 300px;
}
.titleBox {
  height: 60px;
  width: 100px;
  color: #5780c6;
  font-weight: bold;
  margin-top: 5px;
  margin-left: 15px;
  align-items: center;
}
.row {
  display: flex;
  justify-content: center;
  margin-top: 1%;
  height: 20%;
}
.body {
  margin-top: 40px;
}
@keyframes blink-effect {
  50% {
    opacity: 0.5;
  }
}
.col1,
.col2 {
  height: 90px;
  width: 40%;
  font-size: 1.3rem;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 0% 1% 0 1%;
}
.col1:hover,
.col2:hover {
  background-color: #90b5ff;
}
.arrow1,
.arrow2 {
  height: 30px;
  width: 30%;
  margin-right: 5px;
  font-size: 1.3rem;
  background: #ffffff;
  margin: 0px;
  margin: 0% 0% 0 1%;
}
.arrow1 {
  padding-right: 70px;
}
.arrow2 {
  padding-left: 70px;
}
.arrow_img {
  width: 30px;
  height: 30px;
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
