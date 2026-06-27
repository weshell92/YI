<template>
  <section class="glass-panel section-block">
    <div class="section-head">
      <p class="eyebrow">{{ eyebrow }}</p>
      <h2>{{ title }}</h2>
      <p v-if="description">{{ description }}</p>
    </div>
    <slot />
  </section>
</template>

<script setup lang="ts">
defineProps<{
  eyebrow: string;
  title: string;
  description?: string;
}>();
</script>

<style scoped>
.section-block {
  padding: 28px;
}

.section-head {
  margin-bottom: 20px;
}

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  font-size: 0.78rem;
  margin: 0 0 10px;
}

h2 {
  font-family: "Noto Serif SC", "Source Han Serif SC", serif;
  margin: 0 0 10px;
  font-size: 1.6rem;
}

p {
  margin: 0;
  color: var(--text-soft);
  line-height: 1.8;
}
</style>
