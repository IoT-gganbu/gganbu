<template>
  <div class="home">
    <nav-bar type="접수처" department="소아" professorName="전승준" isLogin="false" />
    <patient-receipt-view />
  </div>
</template>

<script>
import PatientReceiptView from "@/views/PatientReceiptView.vue";

export default {
  name: "HomeView",
  components: {
    // HelloWorld,
    PatientReceiptView,
  },
};
</script>
