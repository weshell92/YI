<template>
  <div class="yao-stroke" :class="{ yang: isYang, compact }">
    <template v-if="isYang">
      <span class="full-bar"></span>
    </template>
    <template v-else>
      <span class="half-bar"></span>
      <span class="gap"></span>
      <span class="half-bar"></span>
    </template>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  isYang: boolean;
  compact?: boolean;
}>();
</script>

<style scoped>
.yao-stroke {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 10px;
}

.yao-stroke.compact {
  height: 6px;
}

.full-bar {
  width: 100%;
  height: 100%;
  border-radius: 3px;
  background: var(--brand, #234b40);
}

.half-bar {
  width: 42%;
  height: 100%;
  border-radius: 3px;
  background: var(--brand, #234b40);
}

.gap {
  width: 16%;
  height: 100%;
}

.yao-stroke.compact .gap {
  width: 12%;
}

.yao-stroke.compact .half-bar {
  width: 44%;
}
</style>
