<template>
  <div>
    <div class="box">
      <div class="boxIn"><p id="text">문진을 시작하시겠습니까?</p></div>
      <router-link :to="{ name: 'pre' }" class="link"><custom-button btnText="시작"></custom-button></router-link>
    </div>
  </div>
</template>

<script>
export default {
  methods: {},
};
</script>

<style scoped>
.box {
  width: 80%;
  height: 650px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
  font-size: 2em;
  justify-content: center;
}

.link {
  text-decoration: none;
}

.start {
  width: 180px;
  height: 80px;
  border-radius: 50px;
  background: linear-gradient(45deg, #5780c6, #90b5ff);
  color: white;
  line-height: 80px;
  margin: auto;
}
</style>
