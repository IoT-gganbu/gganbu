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
    path: "/location",
    name: "location",
    component: () => import("../views/CurrentLocationView.vue"),
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
  {
    path: "/examination",
    name: "examination",
    component: () => import("../views/ExaminationProgressView.vue"),
  },
  {
    path: "/qr",
    name: "qr",
    component: () => import("../views/QRRegisterView.vue"),
  },
  {
    path: "/gameChoice",
    name: "gameChoice",
    component: () => import("../views/GameChoiceView.vue"),
  },
  {
    path: "/cardGame",
    name: "cardGame",
    component: () => import("../views/CardGameView.vue"),
  },
  {
    path: "/poseGame",
    name: "poseGame",
    component: () => import("../views/PoseGameView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
