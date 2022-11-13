<template>
  <div class="addPatient">
    <div class="input">
      <div class="label">
        <div class="name">
          <div class="namelabel">성명</div>
          <div class="nameInput">
            <input type="text" placeholder="성명을 입력하세요." v-model="name" />
          </div>
        </div>
        <div class="gender">
          <div class="genderlabel">성별</div>
          <div class="genderInput">
            <custom-button class="submit" id="gender" btnText="남" style="margin-right: 10px" @click="genderClick(0)"></custom-button>
            <custom-button class="submit" id="gender" btnText="여" style="margin-left: 10px" @click="genderClick(1)"></custom-button>
          </div>
        </div>
        <div class="personalNum">
          <div class="personalNumlabel">주민등록번호</div>
          <div class="personalNumInput">
            <input type="text" placeholder="생년월일 6자리" v-model="personalIdFront" />
            <p>-</p>
            <input type="password" placeholder="주민번호 뒷자리" v-model="personalIdBack" />
          </div>
        </div>
        <div class="phoneNum">
          <div class="phoneNumlabel">휴대폰 번호</div>
          <div class="phoneNumInput">
            <input type="text" placeholder="010" v-model="phoneNumFront" />
            <p>-</p>
            <input type="text" placeholder="1234" v-model="phoneNumMid" />
            <p>-</p>
            <input type="text" placeholder="5678" v-model="phoneNumBack" />
          </div>
        </div>
      </div>
    </div>
    <div class="button">
      <custom-button class="submit" btnText="등록" @click="addPatientToDatabase()"></custom-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      gender: 0,
      personalIdFront: "",
      personalIdBack: "",
      phoneNumFront: "",
      phoneNumMid: "",
      phoneNumFBack: "",
      genderClicked: false,
    };
  },
  methods: {
    addPatientToDatabase() {
      console.log(this.name, this.gender);
    },
    genderClick(num) {
      if (num == 0) {
        event.target.addClass("clicked");
        document.getElementById("man").addClass("clicked");
      } else if (num == 1) {
        console.log("여");
      }
    },
  },
};
</script>

<style scoped>
input:focus {
  outline: 2px solid #90b5ff;
}
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
.gender:focus {
  background: #90b5ff;
}
</style>
