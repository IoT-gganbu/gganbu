<template>
  <div class="processDetail">
    <div class="img">{{ imgs[No].url }}</div>
    <div class="description">{{ descriptions[No].text }}</div>
  </div>
</template>

<script>
export default {
  props: { No: Number },
  data() {
    return {
      imgs: [
        {
          url: "aaa",
        },
        {
          url: "bbb",
        },
        {
          url: "ccc",
        },
        {
          url: "ddd",
        },
        {
          url: "eee",
        },
        {
          url: "fff",
        },
      ],
      descriptions: [
        {
          text: "건강검진표 및 문진표를 작성하는 단계로, 건강검진 전 환자로부터 병에 대한 증상과 이전의 병력에 대해 묻는 과정을 말합니다.",
        },
        {
          text: "bbb",
        },
        {
          text: "ccc",
        },
        {
          text: "ddd",
        },
        {
          text: "eee",
        },
        {
          text: "fff",
        },
      ],
    };
  },
};
</script>

<style></style>
