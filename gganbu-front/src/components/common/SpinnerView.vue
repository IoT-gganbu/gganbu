<template>
  <div>
    <div class="text-center justify-content-between">
      <b-spinner v-for="variant in variants" :variant="variant" :key="variant" type="grow"></b-spinner>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      variants: ["primary"],
    };
  },
};
</script>
