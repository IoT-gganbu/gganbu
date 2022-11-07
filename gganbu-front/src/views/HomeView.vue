<template>
  <div class="homeview">
    <router-link to="/process" class="choiceBox">
      <div class="boxIn">
        <div class="icon">
          <img src="@/assets/img/information-outline.png" class="img" />
        </div>
        <div class="title">
          <p class="textp">건강검진<br />절차 안내</p>
        </div>
      </div>
    </router-link>
    <router-link to="/examination" class="choiceBox">
      <div class="boxIn">
        <div class="icon">
          <img src="@/assets/img/stethoscope.png" class="img" />
        </div>
        <div class="title">
          <p class="textp">건강검진<br />진행 상황</p>
        </div>
      </div>
    </router-link>
    <router-link to="/gameChoice" class="choiceBox">
      <div class="boxIn">
        <div class="icon">
          <img src="@/assets/img/controller.png" />
        </div>
        <div class="title">
          <p class="textp">게임</p>
          <p>즐길 거리</p>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.homeview {
  margin-top: 20px;
}
.choiceBox {
  width: 70%;
  height: 170px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  align-items: center;
  margin: 9% 1% 0 1%;
  text-decoration: none;
}
.choiceBox:hover {
  background-color: #90b5ff;
}
.boxIn {
  display: flex;
  text-align: center;
}
.icon {
  margin: 0 10% 0 10%;
}
.img {
  margin-top: 0px;
}

.title {
  font-size: 30px;
  width: 100%;
  color: #5780c6;
  font-weight: bold;
  margin: auto 0 auto -20px;
}

.textp {
  margin: 0;
}
</style>
