<template>
  <div class="home">
    <!-- <HelloWorld msg="Welcome to Your Vue.js App" /> -->
    <patient-receipt-view />
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";
import PatientReceiptView from "@/views/PatientReceiptView.vue";

export default {
  name: "HomeView",
  components: {
    // HelloWorld,
    PatientReceiptView,
  },
};
</script>
