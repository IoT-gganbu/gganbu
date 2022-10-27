<template>
  <div class="addPatient">
    <div class="input">
      <div class="label">
        <div class="name">
          <div class="namelabel">성명</div>
          <div class="nameInput">
            <input type="text" placeholder="성명을 입력하세요." />
          </div>
        </div>
        <div class="gender">
          <div class="genderlabel">성별</div>
          <div class="genderInput">
            <custom-button class="submit" btnText="남" style="margin-right: 10px"></custom-button>
            <custom-button class="submit" btnText="여" style="margin-left: 10px"></custom-button>
          </div>
        </div>
        <div class="personalNum">
          <div class="personalNumlabel">주민등록번호</div>
          <div class="personalNumInput">
            <input type="text" placeholder="생년월일 6자리" />
            <p>-</p>
            <input type="password" placeholder="주민번호 뒷자리" />
          </div>
        </div>
        <div class="phoneNum">
          <div class="phoneNumlabel">휴대폰 번호</div>
          <div class="phoneNumInput">
            <input type="text" placeholder="010" />
            <p>-</p>
            <input type="text" placeholder="1234" />
            <p>-</p>
            <input type="text" placeholder="5678" />
          </div>
        </div>
      </div>
    </div>
    <div class="button">
      <custom-button class="submit" btnText="등록"></custom-button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.submit {
  width: 60%;
}

.label {
  margin: 50px 0px 35px 0px;
}

.namelabel,
.genderlabel,
.personalNumlabel,
.phoneNumlabel {
  line-height: 36px;
  width: 30%;
}

.name,
.gender,
.personalNum,
.phoneNum {
  display: flex;
  margin: 4% 5% 0 4%;
  color: #5780c6;
  font-size: 18px;
  font-weight: 300;
}

.nameInput,
.genderInput,
.personalNumInput,
.phoneNumInput {
  width: 60%;
  display: flex;
  text-align: center;
}
.nameInput input,
.personalNumInput input,
.phoneNumInput input {
  width: 100%;
  border-radius: 5px;
  border: 2px solid #90b5ff;
  height: 30px;
}
p {
  line-height: 36px;
  height: 36px;
  margin: 0 5px 0 5px;
}
</style>
