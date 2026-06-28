<template>
  <div class="page-container">
    <header class="glass-panel hero">
      <div>
        <p class="eyebrow">Hexagram Index</p>
        <h1 class="page-title">六十四卦卦牌总览</h1>
        <p class="page-subtitle">
          每一张卦牌都固定展示序号、卦名、卦符、上下卦结构、关键词和一句话解释。
          先看结构，再决定进哪一卦详情页继续读原文与十翼。
        </p>
      </div>

      <div class="search-panel">
        <label class="search-label" for="hexagram-search">搜索卦名、结构或关键词</label>
        <el-input
          id="hexagram-search"
          v-model="keyword"
          placeholder="例如：需、履、等待、风险、坎上乾下"
        />
        <p class="result-count">当前共 {{ filteredHexagrams.length }} 条结果</p>
      </div>
    </header>

    <section class="glass-panel featured-section">
      <div class="section-head">
        <div>
          <p class="eyebrow">Featured</p>
          <h2>经典卦先看这一组</h2>
        </div>
        <p>它们覆盖了起步、等待、规矩、风险、增减与收束这些最常见的现实问题。</p>
      </div>

      <div class="featured-grid">
        <RouterLink
          v-for="item in featuredHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="featured-card"
        >
          <HexagramGlyph :pattern="item.hexagramSymbol" compact />
          <div>
            <span class="sequence">第 {{ item.sequenceNo }} 卦</span>
            <strong>{{ item.name }}</strong>
            <p>{{ item.hexagramCardTitle }}</p>
          </div>
        </RouterLink>
      </div>
    </section>

    <section class="hexagram-grid">
      <RouterLink
        v-for="item in filteredHexagrams"
        :key="item.id"
        :to="`/hexagrams/${item.id}`"
        class="glass-panel hexagram-card"
      >
        <div class="card-top">
          <div>
            <span class="sequence">第 {{ item.sequenceNo }} 卦</span>
            <h2>{{ item.name }}</h2>
            <p class="card-title">{{ item.hexagramCardTitle }}</p>
          </div>
          <HexagramGlyph :pattern="item.hexagramSymbol" compact :aria-label="`${item.name}卦卦符`" />
        </div>

        <p class="structure">{{ item.structure }}</p>
        <div class="keyword-row">
          <span v-for="keywordItem in item.keywords" :key="keywordItem">{{ keywordItem }}</span>
        </div>
        <p class="summary">{{ item.shortDescription }}</p>
      </RouterLink>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import HexagramGlyph from '../components/HexagramGlyph.vue';
import { fetchHexagrams } from '../api/content';
import type { HexagramSummary } from '../types';

const keyword = ref('');
const hexagrams = ref<HexagramSummary[]>([]);

const featuredIds = [1, 2, 3, 5, 10, 29, 41, 63, 64];

const filteredHexagrams = computed(() => {
  const query = keyword.value.trim();
  if (!query) {
    return hexagrams.value;
  }
  return hexagrams.value.filter((item) => {
    const source = [item.name, item.hexagramCardTitle, item.structure, item.shortDescription, ...item.keywords].join(' ');
    return source.includes(query);
  });
});

const featuredHexagrams = computed(() =>
  featuredIds
    .map((id) => hexagrams.value.find((item) => item.id === id))
    .filter((item): item is HexagramSummary => Boolean(item))
);

onMounted(async () => {
  hexagrams.value = await fetchHexagrams();
});
</script>

<style scoped>
.hero,
.featured-section,
.hexagram-card {
  padding: 28px;
}

.hero {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(280px, 0.8fr);
  gap: 24px;
}

.eyebrow {
  margin: 0 0 12px;
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.search-panel {
  display: grid;
  gap: 12px;
  align-content: start;
}

.search-label,
.result-count,
.section-head p,
.structure,
.summary,
.card-title {
  color: var(--text-soft);
}

.featured-section {
  margin-top: 24px;
}

.section-head {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: end;
}

.section-head h2 {
  margin: 0;
  color: var(--brand);
  font-family: 'Noto Serif SC', 'Source Han Serif SC', serif;
}

.featured-grid {
  margin-top: 20px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
}

.featured-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 18px;
  border-radius: 20px;
  background: var(--surface-strong);
  border: 1px solid var(--border);
}

.featured-card strong,
.hexagram-card h2 {
  color: var(--brand);
}

.sequence {
  color: var(--accent);
  font-size: 0.9rem;
}

.hexagram-grid {
  margin-top: 24px;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}

.hexagram-card {
  display: block;
}

.card-top {
  display: flex;
  justify-content: space-between;
  gap: 18px;
}

.card-top h2 {
  margin: 6px 0 8px;
}

.keyword-row {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

.keyword-row span {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
  font-size: 0.88rem;
}

.summary {
  line-height: 1.8;
}

@media (max-width: 960px) {
  .hero,
  .featured-grid,
  .hexagram-grid,
  .section-head {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
