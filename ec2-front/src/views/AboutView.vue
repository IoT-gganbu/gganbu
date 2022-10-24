<template>
  <div class="about">
    <custom-modal class="updateUserProfileModal" id="updateUserProfileModal" v-show="showImgModal" @close-modal="showImgModal = false" titleText="프로필 사진 변경">
      <cotent>
        <modify-profile></modify-profile>
      </cotent>
    </custom-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showImgModal: true,
    };
  },
};
</script>
