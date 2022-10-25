<template>
  <div class="navbar">
    <div class="logo">
      <img src="@/assets/img/ssabeulans_logo.png" />
    </div>
    <div class="type">{{ type }}</div>
    <div class="right-content" v-if="isLogin == 'true'">
      <div class="professor" v-if="type == '의료진'">{{ department }}과 {{ professorName }}교수님</div>
      <button type="button"><img src="@/assets/img/logout.png" /></button>
    </div>
    <div class="right-content-robot" v-if="isLogin == 'robot'">
      <button type="button">현재위치 보기</button>
      <div class="weatherForm">
        <div id="weather"><img src="" /></div>
        <div id="time"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "navBar",
  props: {
    type: String,
    isLogin: {
      default: "false",
    },
    isRobot: {
      default: "false",
    },
    department: String,
    professorName: String,
  },
};
</script>

<style>
.navbar {
  width: 100%;
  height: 60px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.logo {
  float: left;
  margin-left: 15px;
  line-height: 60px;
}
.logo img {
  width: 200px;
  height: 50px;
  vertical-align: middle;
}
.type {
  float: left;
  padding-top: 26px;
  margin-right: 100px;
  color: #5780c6;
}
.right-content {
  float: right;
}
.right-content-robot {
  float: right;
  font-size: 20px;
  color: #5780c6;
}
.professor {
  padding-top: 20px;
  padding-right: 50px;
  color: #5780c6;
  font-size: 20px;
  float: left;
}
.right-content button {
  background-color: transparent;
  background-position: 0px 0px;
  border: none;
  cursor: pointer;
  vertical-align: middle;
  margin-top: 15px;
  margin-right: 15px;
  float: right;
}
.right-content button img {
  height: 30px;
  width: 30px;
  vertical-align: middle;
}
.right-content-robot button {
  background-color: transparent;
  background-position: 0px 0px;
  border: none;
  cursor: pointer;
  vertical-align: middle;
  margin-top: 15px;
  margin-right: 15px;
  float: right;
  font-size: 20px;
  color: #5780c6;
}
</style>
