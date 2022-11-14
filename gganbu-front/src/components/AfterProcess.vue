<template>
  <div>
    <div>다음 검사실로 안내 받으시겠습니까?</div>
    <custom-button btnText="네" @click="toLoading(), $emit('close-modal')"></custom-button>
  </div>
</template>

<script>
export default {
  methods: {
    toLoading() {
      this.$router.push("/loading");
    },
  },
};
</script>

<style></style>
