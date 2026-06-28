<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <p class="eyebrow">Eight Trigrams</p>
      <h1 class="page-title">八卦列表</h1>
      <p class="page-subtitle">
        八卦是进入六十四卦之前最重要的一层基础。每一卦都对应一种自然象、一种性格倾向和一类现实场景。
      </p>
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
            <h2>{{ trigram.name }}卦</h2>
            <p>{{ trigram.nature }} · {{ trigram.element }}</p>
          </div>
        </div>

        <p class="lead">{{ trigram.plainExplanation }}</p>

        <div class="detail-list">
          <span><strong>性质</strong>{{ trigram.attribute }}</span>
          <span><strong>人事含义</strong>{{ trigram.humanMeaning }}</span>
          <span><strong>现代场景</strong>{{ trigram.modernScenario }}</span>
          <span><strong>适合处理</strong>{{ trigram.suitableProblems }}</span>
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

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin: 0 0 12px;
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

.lead {
  color: var(--text-soft);
  line-height: 1.8;
}
</style>
