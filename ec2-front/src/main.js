import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import customModal from "@/components/common/customModal";
import customButton from "@/components/common/customButton";
import navBar from "@/components/common/navBar";

Vue.config.productionTip = false;
Vue.component("customButton", customButton);
Vue.component("customModal", customModal);
Vue.component("navBar", navBar);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
