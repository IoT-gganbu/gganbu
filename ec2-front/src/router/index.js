import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../views/LoginView.vue"),
  },
  {
    path: "/patientReceiptView",
    name: "patientReceiptView",
    component: () => import("../views/PatientReceiptView.vue"),
  },
  {
    path: "/qr",
    name: "qr",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ "../views/QRCheckinView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
