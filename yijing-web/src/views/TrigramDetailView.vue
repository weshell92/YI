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
        <h2>现实应用场景</h2>
        <p>{{ trigram.modernScenario }}</p>
      </section>
      <section class="glass-panel detail-card">
        <h2>适合解决的问题</h2>
        <p>{{ trigram.suitableProblems }}</p>
      </section>
    </div>

    <section class="glass-panel detail-card">
      <h2>易错理解</h2>
      <p>{{ trigram.commonPitfall }}</p>
    </section>

    <section class="glass-panel detail-card" v-if="relatedHexagrams.length">
      <h2>由这个八卦延伸出的典型六十四卦</h2>
      <div class="section-grid three-col">
        <RouterLink
          v-for="item in relatedHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="glass-panel related-card"
        >
          <strong>{{ item.name }}</strong>
          <p>{{ item.structure }}</p>
          <p>{{ item.shortDescription }}</p>
        </RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import type { HexagramSummary, Trigram } from '../types';
import { fetchHexagrams, fetchTrigram } from '../api/content';

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
  trigram.value = await fetchTrigram(route.params.id as string);
  hexagrams.value = await fetchHexagrams();
});
</script>

<style scoped>
.detail-header,
.detail-card,
.related-card {
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

.related-card {
  display: block;
}
</style>
