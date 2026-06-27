<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <h1 class="page-title">八卦模块</h1>
      <p class="page-subtitle">八卦是六十四卦的前置层。先理解象、性质和现代场景，再进入组合后的具体卦象。</p>
    </header>

    <div class="section-grid two-col trigram-grid">
      <RouterLink
        v-for="trigram in trigrams"
        :key="trigram.id"
        :to="`/trigrams/${trigram.id}`"
        class="glass-panel trigram-card"
      >
        <div class="trigram-top">
          <span class="symbol">{{ trigram.symbol }}</span>
          <div>
            <h2>{{ trigram.name }}</h2>
            <p>{{ trigram.nature }} · {{ trigram.element }}</p>
          </div>
        </div>
        <p>{{ trigram.plainExplanation }}</p>
        <div class="detail-list">
          <span><strong>性质</strong>{{ trigram.attribute }}</span>
          <span><strong>人事</strong>{{ trigram.humanMeaning }}</span>
          <span><strong>场景</strong>{{ trigram.modernScenario }}</span>
        </div>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import type { Trigram } from '../types';
import { fetchTrigrams } from '../api/content';

const trigrams = ref<Trigram[]>([]);

onMounted(async () => {
  trigrams.value = await fetchTrigrams();
});
</script>

<style scoped>
.page-header,
.trigram-card {
  padding: 28px;
}

.trigram-grid {
  margin-top: 24px;
}

.trigram-card {
  display: block;
  transition: transform 0.2s ease;
}

.trigram-card:hover {
  transform: translateY(-4px);
}

.trigram-top {
  display: flex;
  align-items: center;
  gap: 18px;
}

.symbol {
  font-size: 3rem;
  color: var(--brand);
}
</style>

