<template>
  <div class="title">
    <h2 class="modalTitle">{{ name }}{{ titleText }}</h2>
    <slot />
  </div>
</template>

<script>
export default {
  props: {
    titleText: String,
    name: String,
  },
};
</script>

<style>
.modalTitle {
  font-family: "Pretendard Bold";
  font-size: 25px;
  color: #5780c6;
  text-align: left;
  width: fit-content;
  margin: 8% 0 8% 5%;
  position: relative;
}

.modalTitle:after {
  content: "";
  width: 130%;
  display: block;
  position: absolute;
  margin-top: 5px;
  border-bottom: 3px solid #90b5ff;
}
</style>
