<template>
  <div class="logInPage">
    <img class="mainPicture" src="../assets/img/hospital.png" style="height: 630px; width: 500px" />
    <div class="logInInfo">
      <div class="logInBox">
        <div v-show="loginAlram" class="logInAlarm">아이디 또는 비밀번호를 확인하세요.</div>
        <custom-title titleText="로그인"></custom-title>
        <div class="logInLabel">아이디</div>
        <div class="logInInput">
          <input type="text" placeholder="아이디를 입력하세요." id="logInInput" class="logInIdInput" />
        </div>
        <div class="logInLabelPw">비밀번호</div>
        <div class="logInInput">
          <input type="password" id="logInpageInput" @keyup.enter="loginMember()" placeholder="비밀번호를 입력하세요." class="logInPwInput" />
        </div>
        <customButton btnText="로그인" class="idPwSearch" @click="loginMember()"></customButton>
        <customButton btnText="아이디/비밀번호 찾기" class="idPwSearch" @click="findIdpwShowModal = true"></customButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.mainPicture,
.logInInfo {
  display: flex;
  width: 40%;
  height: 100%;
}
.logInPage {
  display: flex;
  justify-content: center;
  height: 85%;
}
.logInBox {
  box-sizing: border-box;
  position: relative;
  width: 100%;
  height: 50%;
  top: 0;
  vertical-align: middle;
}
.logInAlarm {
  display: flex;
  justify-content: right;
  color: #ff6c6c;
  font-size: 10%;
  margin: 4% 2% 0 0;
}
.logInHrStyle {
  display: flex;
  width: 40%;
  height: 1px;
  border-radius: 5px;
  margin: 3px 0 0 5%;
  background-color: #90b5ff;
  margin-left: 13%;
}
.logInIdInput[type="text"] {
  display: flex;
  margin: 1% 0 3% 15%;
  width: 70%;
  border-radius: 5px;
  border: 2px solid #90b5ff;
  height: 30px;
}
.logInPwInput[type="password"] {
  display: flex;
  margin: 1% 0 8% 15%;
  width: 70%;
  border-radius: 5px;
  border: 2px solid #90b5ff;
  height: 30px;
}
.logInLabel {
  display: flex;
  margin: 5% 0 0 15%;
  color: #90b5ff;
  font-size: 13px;
  font-weight: 600;
}
.logInLabelPw {
  display: flex;
  margin: 5% 0 0 15%;
  color: #90b5ff;
  font-size: 13px;
  font-weight: 600;
}
input::placeholder {
  color: #90b5ff;
}
.idPwSearch {
  display: flex;
  margin: 2% 0 3% 15%;
  width: 72%;
  justify-content: center;
}
.signUp {
  display: flex;
  margin: 2% 0 8% 15%;
  width: 72%;
  justify-content: center;
}
</style>
