<template>
  <div id="app">
    <nav-bar type="접수처" department="" professorName="전승준" isLogin="false" />
    <router-view />
  </div>
</template>

<style>
body {
  margin: 0;
}
#app {
  font-family: "Pretendard";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

@font-face {
  font-family: "Pretendard Black";
  src: url("@/assets/font/Pretendard-Black.otf");
}

@font-face {
  font-family: "Pretendard Bold";
  src: url("@/assets/font/Pretendard-Bold.otf");
  font-weight: 700;
}

@font-face {
  font-family: "Pretendard ExtraBold";
  src: url("@/assets/font/Pretendard-ExtraBold.otf");
  font-weight: 900;
}

@font-face {
  font-family: "Pretendard ExtraLight";
  src: url("@/assets/font/Pretendard-ExtraLight.otf");
  font-weight: 100;
}

@font-face {
  font-family: "Pretendard Light";
  src: url("@/assets/font/Pretendard-Light.otf");
  font-weight: 300;
}

@font-face {
  font-family: "Pretendard Medium";
  src: url("@/assets/font/Pretendard-Medium.otf");
  font-weight: 400;
}

@font-face {
  font-family: "Pretendard";
  src: url("@/assets/font/Pretendard-Regular.otf");
  font-weight: 500;
}

@font-face {
  font-family: "Pretendard semiBold";
  src: url("@/assets/font/Pretendard-SemiBold.otf");
  font-weight: 600;
}

@font-face {
  font-family: "Pretendard thin";
  src: url("@/assets/font/Pretendard-Thin.otf");
  font-weight: 200;
}
</style>
