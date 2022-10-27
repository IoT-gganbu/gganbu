import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import customModal from "@/components/common/customModal";
import customButton from "@/components/common/customButton";

import patientList from "@/components/patient/patientList";
import patientCard from "@/components/patient/patientCard";

import navBar from "@/components/common/navBar";
import customTitle from "@/components/common/customTitle";

Vue.config.productionTip = false;
Vue.component("customButton", customButton);
Vue.component("customModal", customModal);

Vue.component("patientList", patientList);
Vue.component("patientCard", patientCard);

Vue.component("navBar", navBar);
Vue.component("customTitle", customTitle);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
