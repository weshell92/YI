<template>
  <div class="page-container" v-if="trigram">
    <section class="glass-panel detail-header">
      <div class="trigram-heading">
        <span class="symbol">{{ trigram.symbol }}</span>
        <div>
          <h1 class="page-title">{{ trigram.name }}卦</h1>
          <p class="page-subtitle">{{ trigram.nature }} · {{ trigram.element }} · {{ trigram.attribute }}</p>
        </div>
      </div>
      <div class="section-grid two-col">
        <div class="glass-panel inner-card">
          <h3>白话解释</h3>
          <p>{{ trigram.plainExplanation }}</p>
        </div>
        <div class="glass-panel inner-card detail-list">
          <span><strong>象</strong>{{ trigram.nature }}</span>
          <span><strong>性质</strong>{{ trigram.attribute }}</span>
          <span><strong>人事</strong>{{ trigram.humanMeaning }}</span>
          <span><strong>场景</strong>{{ trigram.modernScenario }}</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import type { Trigram } from '../types';
import { fetchTrigram } from '../api/content';

const route = useRoute();
const trigram = ref<Trigram | null>(null);

onMounted(async () => {
  trigram.value = await fetchTrigram(route.params.id as string);
});
</script>

<style scoped>
.detail-header {
  padding: 28px;
}

.trigram-heading {
  display: flex;
  gap: 18px;
  align-items: center;
  margin-bottom: 24px;
}

.symbol {
  font-size: 4rem;
  color: var(--brand);
}

.inner-card {
  padding: 20px;
}
</style>

