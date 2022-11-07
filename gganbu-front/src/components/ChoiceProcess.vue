<template>
  <div class="choiceBox">
    <div class="boxIn">
      <div>
        <i class="fa-solid fa-circle-info fa-7x" style="color: #90b5ff"></i>
      </div>
      <div>
        <img src="@/assets/img/ssabeulans_logo.png" style="width: 50px; height: 30px" />
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.choiceBox {
  width: 80%;
  height: 170px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
}
.choiceBox:hover {
  background-color: #90b5ff;
  opacity: 0.3;
}
.boxIn {
  display: flex;
  text-align: center;
}
</style>
