<template>
  <div>
    <div class="box">
      <div class="boxIn"><p id="text">문진 시작하실?</p></div>
      <router-link :to="{ name: 'pre' }" class="link"> 시작</router-link>
    </div>
  </div>
</template>

<script>
export default {
  methods: {},
};
</script>

<style>
.box {
  width: 80%;
  height: 600px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
  font-size: 2em;
}
</style>
