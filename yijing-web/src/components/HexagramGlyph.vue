<template>
  <div class="hexagram-glyph" :class="{ compact }" :aria-label="ariaLabel">
    <YaoStroke
      v-for="(char, index) in strokes"
      :key="index"
      :is-yang="char === '1'"
      :compact="compact"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import YaoStroke from './YaoStroke.vue';

const props = defineProps<{
  pattern: string;
  compact?: boolean;
  ariaLabel?: string;
}>();

// pattern 从低位到高位对应初爻到上爻；渲染时上爻在上，所以反转
const strokes = computed(() => {
  const cleaned = props.pattern.replace(/[^01]/g, '').padStart(6, '0');
  return cleaned.split('').reverse();
});
</script>

<style scoped>
.hexagram-glyph {
  display: inline-flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  width: 72px;
  padding: 10px 8px;
  border-radius: 14px;
  background: rgba(35, 75, 64, 0.06);
}

.hexagram-glyph.compact {
  width: 44px;
  gap: 4px;
  padding: 6px;
}
</style>
