<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <p class="eyebrow">Eight Trigrams</p>
      <h1 class="page-title">八卦列表</h1>
      <p class="page-subtitle">
        八卦是六十四卦的前置层。先认识自然象、五行、性质与现代场景，再进入完整卦牌阅读。
      </p>
    </header>

    <div class="trigram-grid">
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
import { fetchTrigrams } from '../api/content';
import type { Trigram } from '../types';

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
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}

.trigram-card {
  display: block;
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

@media (max-width: 960px) {
  .trigram-grid {
    grid-template-columns: 1fr;
  }
}
</style>
