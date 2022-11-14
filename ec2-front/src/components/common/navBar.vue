<template>
  <div class="navbar">
    <div class="logo">
      <img src="@/assets/img/ssabeulans_logo.png" />
    </div>
    <div class="type">{{ type }}</div>
    <div class="right-content" v-if="isLogined">
      <div class="professor" v-if="type == '의료진'">{{ department }}과 {{ professorName }}교수님</div>
      <button type="button" @click="logout()"><img src="@/assets/img/logout.png" /></button>
    </div>
  </div>
</template>

<script>
export default {
  name: "navBar",
  data() {
    return {
      isLogined: sessionStorage.getItem("isLogined"),
      type: sessionStorage.getItem("loginType"),
      department: sessionStorage.getItem("task"),
      professorName: sessionStorage.getItem("name"),
    };
  },
  computed: {
    changeNavbar: function () {
      return this.$store.state.memberStore.isLogined;
    },
  },
  watch: {
    changeNavbar: function () {
      this.isLogined = sessionStorage.getItem("isLogined");
      this.type = sessionStorage.getItem("loginType");
      this.professorName = sessionStorage.getItem("name");
      const departNumber = sessionStorage.getItem("task");

      switch (departNumber) {
        case "2":
        case "3":
          this.department = "건강의학";
          break;
        case "4":
        case "5":
        case "8":
        case "9":
        case "10":
          this.department = "내";
          break;
        case "6":
        case "7":
          this.department = "산부인";
          break;
        default:
          this.department = "진료";
      }
    },
  },
  methods: {
    async logout() {
      this.$store.commit("MEMBER_LOGOUT");
      sessionStorage.clear();
      this.$router.push("/");
    },
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
  align-content: center;
}
</style>
