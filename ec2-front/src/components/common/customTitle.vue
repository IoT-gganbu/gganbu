<template>
  <div class="title">
    <h2 class="modalTitle">{{ titleText }}</h2>
    <slot />
  </div>
</template>

<script>
export default {
  props: {
    titleText: String,
  },
};
</script>

<style>
.title {
  margin: 40px 0 40px 40px;
}
.modalTitle {
  font-family: "Pretendard Bold";
  font-size: 35px;
  color: #5780c6;
  text-align: left;
  width: fit-content;
  margin-left: 2rem;
  position: relative;
}

.modalTitle:after {
  content: "";
  width: 140%;
  display: block;
  position: absolute;
  margin-top: 5px;
  border-bottom: 4px solid #90b5ff;
}
</style>
