<template>
  <div class="homeview">
    <router-link to="/poseGame" class="choiceBox">
      <custom-title titleText="모션 인식 게임" />
      <div class="boxIn">
        <div class="icon">
          <img src="@/assets/img/game_pose.png" class="game-img" />
        </div>
      </div>
    </router-link>
    <router-link to="/cardGame" class="choiceBox">
      <custom-title titleText="카드 뒤집기 게임" />
      <div class="boxIn">
        <div class="icon">
          <img src="@/assets/img/game_card.png" class="game-img" />
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {};
</script>

<style>
.homeview {
  margin-top: 20px;
}
.choiceBox {
  width: 70%;
  height: 280px;
  background: #ffffff;
  border: 2px solid #90b5ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  display: inline-grid;
  margin: 9% 1% 0 1%;
  text-decoration: none;
}
.choiceBox:hover {
  background-color: #90b5ff;
}
.boxIn {
  text-align: center;
  object-fit: scale-down;
}
.game-img {
  width: 250px;
  /* height: 150px; */
}
.icon {
  margin: 0 10% 0 10%;
}

.title {
  font-size: 30px;
  width: 100%;
  color: #5780c6;
  font-weight: bold;
  margin-left: 10px;
}
</style>
