import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../views/HomeView.vue"),
  },
  {
    path: "/process",
    name: "process",
    component: () => import("../views/TestProcessView.vue"),
  },
  {
    path: "/qr",
    name: "qr",
    component: () => import("../views/QRRegisterView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
