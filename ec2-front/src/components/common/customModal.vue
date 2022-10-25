<template>
  <div class="modal-overlay" @click="$emit('close-modal')">
    <div id="modalMain" class="modal" @click.stop>
      <div class="modalTopLine">
        <i class="fa-solid fa-xmark modalClose" @click="$emit('close-modal')"></i>
      </div>
      <h2 class="modalTitle">{{ titleText }}</h2>
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    titleText: String,
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: rgba(211, 211, 211, 0.4);
}

.modal {
  text-align: center;
  height: 40%;
  width: 40%;
  margin-top: 8%;
  padding: 60px 0;
  background: #ffffff;
  border: 3px solid #90b5ff;
  border-radius: 10px;
  overflow-y: hidden;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

.modalTopLine {
  justify-content: flex-end;
  display: flex;
  margin-bottom: -20px;
  margin-top: -50px;
  font: 36px "Pretendard ExtraBold";
  color: #5780c6;
}

.modalClose {
  margin-right: 1rem;
  cursor: pointer;
}

.modalTitle {
  font-family: "Pretendard Bold";
  font-size: 35px;
  color: #5780c6;
  text-align: left;
  width: fit-content;
  margin-left: 2rem;
  position: relative;
}

.modalTitle:after {
  content: "";
  width: 140%;
  display: block;
  position: absolute;
  margin-top: 5px;
  border-bottom: 4px solid #90b5ff;
}
</style>
