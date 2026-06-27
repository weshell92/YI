<template>
  <div class="page-container" v-if="detail">
    <section class="glass-panel page-header">
      <p class="eyebrow">Hexagram Detail</p>
      <h1 class="page-title">{{ detail.hexagram.name }}</h1>
      <p class="page-subtitle">{{ detail.hexagram.structure }} · {{ detail.hexagram.shortDescription }}</p>
      <div class="keyword-row">
        <span v-for="item in detail.hexagram.keywords" :key="item">{{ item }}</span>
      </div>
    </section>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>卦象结构</h2>
        <div class="detail-list">
          <span><strong>上卦</strong>{{ detail.upperTrigram.name }} · {{ detail.upperTrigram.nature }}</span>
          <span><strong>下卦</strong>{{ detail.lowerTrigram.name }} · {{ detail.lowerTrigram.nature }}</span>
          <span><strong>一句话</strong>{{ detail.hexagram.shortDescription }}</span>
          <span><strong>处境</strong>{{ detail.hexagram.situationAnalysis }}</span>
        </div>
      </section>
      <section class="glass-panel detail-card">
        <h2>行动建议</h2>
        <p>{{ detail.hexagram.advice }}</p>
        <div class="detail-list">
          <span><strong>事业</strong>{{ detail.hexagram.careerExplanation }}</span>
          <span><strong>感情</strong>{{ detail.hexagram.loveExplanation }}</span>
          <span><strong>人际</strong>{{ detail.hexagram.relationExplanation }}</span>
          <span><strong>创业</strong>{{ detail.hexagram.businessExplanation }}</span>
        </div>
      </section>
    </div>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>古文原文</h2>
        <div class="detail-list">
          <span><strong>卦辞</strong>{{ detail.hexagram.guaCi }}</span>
          <span><strong>彖辞</strong>{{ detail.hexagram.tuanCi }}</span>
          <span><strong>象辞</strong>{{ detail.hexagram.xiangCi }}</span>
        </div>
      </section>
      <section class="glass-panel detail-card">
        <h2>白话解释与误区</h2>
        <p>{{ detail.hexagram.plainExplanation }}</p>
        <p><strong>常见误解：</strong>{{ detail.hexagram.commonMistakes }}</p>
      </section>
    </div>

    <section class="glass-panel detail-card">
      <h2>六爻</h2>
      <div class="line-grid">
        <article v-for="line in detail.lines" :key="line.id" class="line-card">
          <strong>{{ line.lineName }}</strong>
          <p>{{ line.originalText }}</p>
          <p>{{ line.plainExplanation }}</p>
          <p class="line-note">{{ line.advice }}</p>
        </article>
      </div>
    </section>

    <section class="glass-panel detail-card" v-if="detail.relatedHexagrams.length">
      <h2>相关卦象</h2>
      <div class="section-grid three-col">
        <RouterLink
          v-for="item in detail.relatedHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="glass-panel related-card"
        >
          <strong>{{ item.name }}</strong>
          <p>{{ item.shortDescription }}</p>
        </RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import type { HexagramDetailView as HexagramDetailType } from '../types';
import { fetchHexagram } from '../api/content';

const route = useRoute();
const detail = ref<HexagramDetailType | null>(null);

onMounted(async () => {
  detail.value = await fetchHexagram(route.params.id as string);
});
</script>

<style scoped>
.page-header,
.detail-card,
.related-card {
  padding: 28px;
}

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.keyword-row {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-top: 16px;
}

.keyword-row span {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
}

.detail-grid {
  margin-top: 24px;
}

.line-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}

.line-card {
  border-radius: 20px;
  border: 1px solid var(--border);
  padding: 18px;
  background: rgba(255, 252, 246, 0.72);
}

.line-note {
  color: var(--brand);
}

.related-card {
  display: block;
}
</style>

