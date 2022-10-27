import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import customModal from "@/components/common/customModal";
import customButton from "@/components/common/customButton";
import navBar from "@/components/common/navBar";
import customTitle from "@/components/common/customTitle";

Vue.config.productionTip = false;
Vue.component("customButton", customButton);
Vue.component("customModal", customModal);
Vue.component("navBar", navBar);
Vue.component("customTitle", customTitle);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
