<template>
  <div class="about">
    <!-- 나중에 환자 추가 화면에 추가할 코드 -->
    <custom-modal class="addPatient" id="addPatient" v-show="showImgModal" @close-modal="showImgModal = false" titleText="환자 등록">
      <cotent>
        <add-patient></add-patient>
      </cotent>
    </custom-modal>
  </div>
</template>

<script>
import AddPatient from "@/components/patient/addPatient.vue";

export default {
  components: {
    AddPatient,
  },
  data() {
    return {
      showImgModal: true,
    };
  },
};
</script>

<style scoped>
.addPatient {
  height: 130%;
}
</style>
