<template>
  <div class="page-container" v-if="trigram">
    <section class="glass-panel detail-header">
      <div class="trigram-heading">
        <span class="symbol">{{ trigram.symbol }}</span>
        <div>
          <p class="eyebrow">Trigram Detail</p>
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
          <span><strong>自然象</strong>{{ trigram.nature }}</span>
          <span><strong>五行</strong>{{ trigram.element }}</span>
          <span><strong>核心性质</strong>{{ trigram.attribute }}</span>
          <span><strong>人事含义</strong>{{ trigram.humanMeaning }}</span>
        </div>
      </div>
    </section>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>现代应用场景</h2>
        <p>{{ trigram.modernScenario }}</p>
      </section>
      <section class="glass-panel detail-card">
        <h2>适合处理的问题</h2>
        <p>{{ trigram.suitableProblems }}</p>
      </section>
    </div>

    <section class="glass-panel detail-card">
      <h2>常见误区</h2>
      <p>{{ trigram.commonPitfall }}</p>
    </section>

    <section class="glass-panel detail-card" v-if="relatedHexagrams.length">
      <h2>相关六十四卦</h2>
      <div class="related-grid">
        <RouterLink
          v-for="item in relatedHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="related-card"
        >
          <HexagramGlyph :pattern="item.hexagramSymbol" compact />
          <div>
            <strong>{{ item.name }}</strong>
            <p>{{ item.hexagramCardTitle }}</p>
            <p>{{ item.shortDescription }}</p>
          </div>
        </RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import HexagramGlyph from '../components/HexagramGlyph.vue';
import { fetchHexagrams, fetchTrigram } from '../api/content';
import type { HexagramSummary, Trigram } from '../types';

const route = useRoute();
const trigram = ref<Trigram | null>(null);
const hexagrams = ref<HexagramSummary[]>([]);

const relatedHexagrams = computed(() => {
  if (!trigram.value) {
    return [];
  }
  return trigram.value.relatedHexagramIds
    .map((id) => hexagrams.value.find((item) => item.id === id))
    .filter((item): item is HexagramSummary => Boolean(item));
});

onMounted(async () => {
  const [trigramData, hexagramData] = await Promise.all([
    fetchTrigram(route.params.id as string),
    fetchHexagrams()
  ]);
  trigram.value = trigramData;
  hexagrams.value = hexagramData;
});
</script>

<style scoped>
.detail-header,
.detail-card {
  padding: 28px;
}

.trigram-heading {
  display: flex;
  gap: 18px;
  align-items: center;
  margin-bottom: 24px;
}

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin: 0 0 12px;
}

.detail-grid {
  margin-top: 24px;
}

.symbol {
  font-size: 4rem;
  color: var(--brand);
}

.inner-card {
  padding: 20px;
}

.related-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.related-card {
  display: flex;
  gap: 14px;
  align-items: center;
  padding: 18px;
  border-radius: 18px;
  background: rgba(255, 252, 246, 0.76);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

@media (max-width: 960px) {
  .related-grid,
  .trigram-heading {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
