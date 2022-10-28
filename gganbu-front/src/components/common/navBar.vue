<template>
  <div class="navbar">
    <div class="logo">
      <img src="@/assets/img/ssabeulans_logo.png" />
    </div>
    <div class="right-content">
      <button id="current-place-button" type="button">현재위치 보기</button>
      <div class="weather-and-time">
        <div id="weather">맑음 🌞</div>
        <div id="time">{{ now }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "navBar",
  props: {},
  data() {
    return {
      now: "00 : 00",
    };
  },
  mounted() {
    setInterval(this.calcTime, 1000);
    this.getWeather();
  },
  methods: {
    calcTime() {
      const time = new Date();

      this.now = time.getHours() + " : " + time.getMinutes();
    },
    getWeather() {
      const date = new Date();
      const today = date.getFullYear() + "" + (date.getMonth() + 1) + "" + date.getDate();
      const link = `http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=${process.env.VUE_APP_WEATHER_API_KEY}&base_date=${today}&base_time=0630&nx=36.3549777&ny=127.2983403`;

      this.$axios
        .get(link)
        .then((response) => {
          console.log(JSON.stringify(response));
          // response.data.body.
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.navbar {
  width: 100%;
  height: 60px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  background-color: white;
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
  vertical-align: middle;
  margin-right: 10px;
}
#current-place-button {
  background-color: transparent;
  background-position: 0px 0px;
  border: none;
  cursor: pointer;
  color: #5780c6;
  font-size: 16px;
  margin-top: 20px;
  margin-right: 20px;
}
.weather-and-time {
  float: right;
  height: 60px;
  vertical-align: middle;
  font-weight: bold;
  font-size: 15px;
  margin-top: 10px;
}
#weather {
}
#time {
}
</style>
